package com.youlai.boot.module.leave.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.boot.core.exception.BusinessException;
import com.youlai.boot.module.leave.converter.LeaveConverter;
import com.youlai.boot.module.leave.enums.LeaveStatusEnum;
import com.youlai.boot.module.leave.enums.LeaveStepEnum;
import com.youlai.boot.module.leave.mapper.LeaveApprovalRecordMapper;
import com.youlai.boot.module.leave.mapper.LeaveMapper;
import com.youlai.boot.module.leave.model.entity.LeaveApprovalRecord;
import com.youlai.boot.module.leave.model.entity.LeaveRequest;
import com.youlai.boot.module.leave.model.form.LeaveApproveForm;
import com.youlai.boot.module.leave.model.form.LeaveForm;
import com.youlai.boot.module.leave.model.vo.LeaveListVo;
import com.youlai.boot.module.leave.model.vo.LeavePendingVo;
import com.youlai.boot.module.leave.model.vo.LeaveRecordVo;
import com.youlai.boot.module.leave.service.LeaveService;
import com.youlai.boot.security.util.SecurityUtils;
import com.youlai.boot.system.model.entity.User;
import com.youlai.boot.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LeaveServiceImpl extends ServiceImpl<LeaveMapper, LeaveRequest> implements LeaveService {

    private final LeaveConverter leaveConverter;

    private final LeaveApprovalRecordMapper leaveApprovalRecordMapper;

    private final UserService userService;

    @Override
    public boolean addLeave(LeaveForm formData) {
        LeaveRequest entity = new LeaveRequest();
        entity.setStartTime(formData.getStartTime());
        entity.setEndTime(formData.getEndTime());
        entity.setStatus(LeaveStatusEnum.PENDING.getValue());
        entity.setCurrentStep(LeaveStepEnum.STEP1.getValue());
        entity.setReason(formData.getReason());
        entity.setUserId(SecurityUtils.getUserId());
        return this.save(entity);
    }

    @Override
    public List<LeaveListVo> listMyLeaves() {
        Long userId = SecurityUtils.getUserId();
        List<LeaveRequest> list =  this.list(new LambdaQueryWrapper<LeaveRequest>()
            .eq(LeaveRequest::getUserId, userId)
            .orderByAsc(LeaveRequest::getCreateTime));

        return leaveConverter.toMineList(list);
    }

    @Override
    public List<LeavePendingVo> listPendingLeaves() {
        Set<String> roles = SecurityUtils.getRoles();
        LambdaQueryWrapper<LeaveRequest> w = new LambdaQueryWrapper<LeaveRequest>()
            .eq(LeaveRequest::getStatus, LeaveStatusEnum.PENDING.getValue())
            .orderByAsc(LeaveRequest::getCreateTime);
        if (roles.contains("DEPT_MANAGER")) {
            Long deptId = userService.getById(SecurityUtils.getUserId()).getDeptId();
            List<Long> userIds = userService.list(
                new LambdaQueryWrapper<User>()
                    .eq(User::getDeptId, deptId)
            ).stream().map(User::getId).toList();
            if (userIds.isEmpty()) {
                return List.of();
            }
            w.eq(LeaveRequest::getCurrentStep, 1).in(LeaveRequest::getUserId, userIds);
        } else if (roles.contains("ADMIN") || SecurityUtils.isRoot()) {
            w.eq(LeaveRequest::getCurrentStep, 2);
        }else {
            return List.of();
        }
        return leaveConverter.toPendingList(this.list(w));
    }

    @Override
    public boolean approveLeave(Long id, LeaveApproveForm form) {

        //1.验证表单存在
        LeaveRequest entity = this.getById(id);
        if (entity == null) throw new BusinessException("请假单不存在");
        //2.状态必须是待审批
        if (entity.getStatus() != LeaveStatusEnum.PENDING.getValue())
            throw new BusinessException("该请假单已审批，不能重复操作");
        //3.currentStep不能是3(已结束)
        if (entity.getCurrentStep() == LeaveStepEnum.STEP3.getValue()) {
            throw new BusinessException("该请假单已审批结束");
        }
        //4.验证是否有部门审批权限
        User applicant = userService.getById(entity.getUserId());
        User approver = userService.getById(SecurityUtils.getUserId());
        if (applicant == null || approver == null) {
            throw new BusinessException("用户不存在");
        }
        //修正，只有审批流程为部门主管才验证部门权限
        if (entity.getCurrentStep() == LeaveStepEnum.STEP1.getValue()) {
            if (!Objects.equals(applicant.getDeptId(), approver.getDeptId())) {
                throw new BusinessException("无权审批其他部门的请假");
            }
        }
        //5.审批角色匹配
        int step = getStep(entity);
        //6.驳回原因不能为空
        int action = form.getAction();
        if ( action == 2 && StrUtil.isBlank(form.getRejectReason())) {
            throw new BusinessException("驳回原因不能为空");
        }
        //7.改从表记录
        LeaveApprovalRecord record = new LeaveApprovalRecord();
        record.setLeaveId(entity.getId());
        record.setStep(entity.getCurrentStep());
        record.setApproverId(approver.getId());
        record.setAction(form.getAction());
        //8.改主表
        if (action == 2) {
            entity.setStatus(LeaveStatusEnum.REJECT.getValue());
            entity.setCurrentStep(LeaveStepEnum.STEP3.getValue());
            entity.setRejectReason(form.getRejectReason());
            record.setComment(form.getRejectReason());//修复通过却更新驳回原因的问题
            entity.setApproveTime(null);
        } else if (step == 1) {
            entity.setStatus(LeaveStatusEnum.PENDING.getValue());
            entity.setCurrentStep(LeaveStepEnum.STEP2.getValue());
        } else if (step == 2) {
            entity.setStatus(LeaveStatusEnum.APPROVE.getValue());
            entity.setCurrentStep(LeaveStepEnum.STEP3.getValue());
            entity.setApproveTime(LocalDateTime.now());
            entity.setRejectReason(null);
        }
        leaveApprovalRecordMapper.insert(record);
        entity.setApproverId(approver.getId());
        return this.updateById(entity);
    }

    @Override
    public List<LeaveRecordVo> getRecords() {
        List<Long> ids = new ArrayList<>();
            this.list(new LambdaQueryWrapper<LeaveRequest>()
            .eq(LeaveRequest::getUserId, SecurityUtils.getUserId()))
            .forEach(item -> ids.add(item.getId()));

            if (ids.isEmpty()) {
                return List.of();
            }
            List<LeaveApprovalRecord> records = leaveApprovalRecordMapper.selectList(
                new LambdaQueryWrapper<LeaveApprovalRecord>()
                    .in(LeaveApprovalRecord::getLeaveId, ids)
                    .orderByAsc(LeaveApprovalRecord::getCreateTime)
            );
         return leaveConverter.toRecordList(records);
    }

    private static int getStep(LeaveRequest entity) {
        int step = entity.getCurrentStep();
        Set<String> roles = SecurityUtils.getRoles();
        if (step == 1) {
            boolean canApprove = roles.contains("DEPT_MANAGER") || SecurityUtils.isRoot();
            if (!canApprove) {
                throw new BusinessException("当前轮到主管审批");
            }
        }else if (step == 2) {
            boolean canApprove = roles.contains("ADMIN") || SecurityUtils.isRoot();
            if (!canApprove) {
                throw new BusinessException("当前轮到经理审批");
            }
        }
        return step;
    }

}

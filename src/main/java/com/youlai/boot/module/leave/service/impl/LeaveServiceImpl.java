package com.youlai.boot.module.leave.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.boot.core.exception.BusinessException;
import com.youlai.boot.module.leave.converter.LeaveConverter;
import com.youlai.boot.module.leave.enums.LeaveStatusEnum;
import com.youlai.boot.module.leave.mapper.LeaveMapper;
import com.youlai.boot.module.leave.model.entity.LeaveRequest;
import com.youlai.boot.module.leave.model.form.LeaveApproveForm;
import com.youlai.boot.module.leave.model.form.LeaveForm;
import com.youlai.boot.module.leave.model.vo.LeaveListVo;
import com.youlai.boot.module.leave.model.vo.LeavePendingVo;
import com.youlai.boot.module.leave.service.LeaveService;
import com.youlai.boot.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveServiceImpl extends ServiceImpl<LeaveMapper, LeaveRequest> implements LeaveService {

    private final LeaveConverter leaveConverter;

    @Override
    public boolean addLeave(LeaveForm formData) {
        LeaveRequest entity = new LeaveRequest();
        entity.setStartTime(formData.getStartTime());
        entity.setEndTime(formData.getEndTime());
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
        List<LeaveRequest> list = this.list(new LambdaQueryWrapper<LeaveRequest>()
            .eq(LeaveRequest::getStatus, LeaveStatusEnum.PENDING.getValue())
            .orderByAsc(LeaveRequest::getCreateTime));

        return leaveConverter.toPendingList(list);
    }

    @Override
    public boolean approveLeave(Long id, LeaveApproveForm form) {
        LeaveRequest entity = this.getById(id);
        if (entity == null) throw new BusinessException("请假单不存在");

        if (entity.getStatus() != LeaveStatusEnum.PENDING.getValue())
            throw new BusinessException("该请假单已审批，不能重复操作");

        if (form.getAction() == 2 ) {
            if (StrUtil.isBlank(form.getRejectReason())) {
                throw new BusinessException("驳回原因不能为空");
            }else {
                entity.setApproveTime(null);
                entity.setRejectReason(form.getRejectReason());
            }
        }else {
            entity.setApproveTime(LocalDateTime.now());
            entity.setRejectReason(null);
        }
        entity.setStatus(form.getAction());
        entity.setApproverId(SecurityUtils.getUserId());
        return this.updateById(entity);
    }

}

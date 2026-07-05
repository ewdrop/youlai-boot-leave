package com.youlai.boot.module.leave.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.boot.module.leave.converter.LeaveConverter;
import com.youlai.boot.module.leave.enums.LeaveStatusEnum;
import com.youlai.boot.module.leave.mapper.LeaveMapper;
import com.youlai.boot.module.leave.model.entity.LeaveRequest;
import com.youlai.boot.module.leave.model.form.LeaveForm;
import com.youlai.boot.module.leave.model.vo.LeaveLIstVo;
import com.youlai.boot.module.leave.model.vo.LeavePendingVo;
import com.youlai.boot.module.leave.service.LeaveService;
import com.youlai.boot.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public List<LeaveLIstVo> listMyLeaves() {
        Long userId = SecurityUtils.getUserId();
        List<LeaveRequest> list =  this.list(new LambdaQueryWrapper<LeaveRequest>()
            .eq(LeaveRequest::getUserId, userId)
            .orderByAsc(LeaveRequest::getCreateTime));

        return leaveConverter.toMineList(list);
    }

    @Override
    public List<LeavePendingVo> listPendingLeaves() {
        List<LeaveRequest> list = this.list(new LambdaQueryWrapper<LeaveRequest>()
            .eq(LeaveRequest::getStatus, LeaveStatusEnum.PENDING)
            .orderByAsc(LeaveRequest::getCreateTime));

        return leaveConverter.toPendingList(list);
    }

}

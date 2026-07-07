package com.youlai.boot.module.leave.converter;

import com.youlai.boot.module.leave.model.entity.LeaveApprovalRecord;
import com.youlai.boot.module.leave.model.entity.LeaveRequest;
import com.youlai.boot.module.leave.model.vo.LeaveListVo;
import com.youlai.boot.module.leave.model.vo.LeavePendingVo;
import com.youlai.boot.module.leave.model.vo.LeaveRecordVo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LeaveConverter {

    LeaveListVo toListVo(LeaveRequest entity);

    List<LeaveListVo> toMineList(List<LeaveRequest> list);

    List<LeavePendingVo> toPendingList(List<LeaveRequest> list);

    List<LeaveRecordVo> toRecordList(List<LeaveApprovalRecord> list);
}

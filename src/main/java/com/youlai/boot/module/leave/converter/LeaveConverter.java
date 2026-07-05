package com.youlai.boot.module.leave.converter;

import com.youlai.boot.module.leave.model.entity.LeaveRequest;
import com.youlai.boot.module.leave.model.vo.LeaveLIstVo;
import com.youlai.boot.module.leave.model.vo.LeavePendingVo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LeaveConverter {

    LeaveLIstVo toListVo(LeaveRequest entity);

    List<LeaveLIstVo> toMineList(List<LeaveRequest> list);

    List<LeavePendingVo> toPendingList(List<LeaveRequest> list);
}

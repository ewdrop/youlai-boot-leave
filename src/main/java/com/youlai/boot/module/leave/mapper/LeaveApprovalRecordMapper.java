package com.youlai.boot.module.leave.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.boot.module.leave.model.entity.LeaveApprovalRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 请假审批阶段Mapper接口
 *
 * @author ewdrop
 * @since 2026-07-06 17:02
 */
@Mapper
public interface LeaveApprovalRecordMapper extends BaseMapper<LeaveApprovalRecord> {
}

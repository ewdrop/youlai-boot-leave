package com.youlai.boot.module.leave.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.boot.module.leave.model.entity.LeaveRequest;
import org.apache.ibatis.annotations.Mapper;

/**
 * 请假审批Mapper接口
 *
 * @author ewdrop
 * @since 2026-07-05 16:23
 */
@Mapper
public interface LeaveMapper extends BaseMapper<LeaveRequest> {

}

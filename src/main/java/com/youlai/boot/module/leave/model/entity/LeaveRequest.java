package com.youlai.boot.module.leave.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("leave_request")
public class LeaveRequest {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String reason;

    private int status;

    private Long approverId;

    private LocalDateTime approveTime;

    private String rejectReason;

    private LocalDateTime createTime;

    private int isDeleted;

}

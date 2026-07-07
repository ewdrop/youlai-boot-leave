package com.youlai.boot.module.leave.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("leave_approval_record")
public class LeaveApprovalRecord {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long leaveId;

    private  int step;

    private Long approverId;

    private int action;

    private String comment;

    private LocalDateTime createTime;
}

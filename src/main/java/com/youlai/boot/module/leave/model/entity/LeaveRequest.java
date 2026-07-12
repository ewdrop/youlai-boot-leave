package com.youlai.boot.module.leave.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.youlai.boot.module.file.model.FileInfo;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName(value = "leave_request", autoResultMap = true)
public class LeaveRequest {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String reason;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<FileInfo> attachment;

    private int status;

    private int currentStep;

    private Long approverId;

    //最近审批结果的时间
    private LocalDateTime approveTime;

    private String rejectReason;

    private LocalDateTime applicantTime;

    private int isDeleted;

}

package com.youlai.boot.module.leave.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "请假审批记录对象")
public class LeaveRecordVo {

    @Schema(description = "请假单ID")
    private Long leaveId;

    @Schema(description = "请假审批阶段")
    private int step;

    @Schema(description = "请假审批人ID")
    private Long approverId;

    @Schema(description = "当前审批人审批结果")
    private int action;

    @Schema(description = "请假审批备注/驳回原因")
    private String comment;

    @Schema(description = "请假创建时间")
    private LocalDateTime createTime;
}

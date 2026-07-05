package com.youlai.boot.module.leave.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "个人请假列表对象")
public class LeaveLIstVo {

    @Schema(description = "请假ID")
    private Long id;

    @Schema(description = "请假开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @Schema(description = "请假结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @Schema(description = "请假原因")
    private String reason;

    @Schema(description = "审批状态")
    private int status;

    @Schema(description = "驳回原因")
    private String rejectReason;

    @Schema(description = "审批时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime approveTime;

}

package com.youlai.boot.module.leave.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.youlai.boot.module.file.model.FileInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Schema(description = "个人请假列表对象")
public class LeaveListVo {

    @Schema(description = "请假ID")
    private Long id;

    @Schema(description = "申请请假时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime applicantTime;

    @Schema(description = "请假开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @Schema(description = "请假结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @Schema(description = "请假原因")
    private String reason;

    @Schema(description = "附件")
    private List<FileInfo> attachment;

    @Schema(description = "审批状态")
    private int status;

    @Schema(description = "当前审批阶段")//级别描述不太恰当，阶段含义更符合
    private int currentStep;

    @Schema(description = "驳回原因")
    private String rejectReason;

    @Schema(description = "审批时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //动态的->当前审批结果的时间，随审批结果更新而更新
    private LocalDateTime approveTime;

}

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
@Schema(description = "待审批请假列表对象")
public class LeavePendingVo {

    @Schema(description = "请假ID")
    private Long id;

    @Schema(description = "申请人Id")
    private Long userId;

    @Schema(description = "申请人昵称")
    private String nickname;

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

    @Schema(description = "申请时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime applicantTime;

}

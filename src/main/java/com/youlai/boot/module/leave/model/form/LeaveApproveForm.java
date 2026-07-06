package com.youlai.boot.module.leave.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@Schema(description = "请假审批对象")
public class LeaveApproveForm {

    @Schema(description = "请假审批结果")
    @Range(min = 1, max = 2, message = "操作类型只能是1或2")
    private int action;

    @Schema(description = "驳回原因")
    private String rejectReason;
}

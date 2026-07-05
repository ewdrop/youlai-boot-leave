package com.youlai.boot.module.leave.enums;

import com.youlai.boot.common.base.IBaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 审批状态类型枚举
 *
 * @author edward
 * @since 2024/10/14
 */
@Getter
@Schema(enumAsRef = true)
public enum LeaveStatusEnum implements IBaseEnum<Integer> {
    PENDING(0,"待审批"),
    APPROVE(1, "批准"),
    REJECT(2, "驳回");

    private final Integer value;

    private final String label;

    LeaveStatusEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}

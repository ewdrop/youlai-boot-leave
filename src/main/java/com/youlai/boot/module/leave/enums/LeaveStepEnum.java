package com.youlai.boot.module.leave.enums;

import com.youlai.boot.common.base.IBaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(enumAsRef = true)
public enum LeaveStepEnum implements IBaseEnum<Integer> {
    STEP1(1, "主管"),
    STEP2(2, "经理"),
    STEP3(3, "结束");

    private final Integer value;

    private final String label;

    LeaveStepEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}

package com.youlai.boot.module.leave.controller;

import com.youlai.boot.core.web.Result;
import com.youlai.boot.module.leave.model.form.LeaveApproveForm;
import com.youlai.boot.module.leave.model.form.LeaveForm;
import com.youlai.boot.module.leave.model.vo.LeaveListVo;
import com.youlai.boot.module.leave.model.vo.LeavePendingVo;
import com.youlai.boot.module.leave.model.vo.LeaveRecordVo;
import com.youlai.boot.module.leave.service.LeaveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "请假审批")
@RestController
@RequestMapping("/api/v1/leave-requests")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    @Operation(summary = "新增请假数据")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('module:leave:create')")
    public Result<?> addLeave(@RequestBody @Valid LeaveForm form) {
        boolean result = leaveService.addLeave(form);
        return Result.judge(result);
    }

    @GetMapping("/mine")
    @PreAuthorize("@ss.hasPerm('module:leave:mine')")
    public Result<List<LeaveListVo>> listMyLeaves() {
        return Result.success(leaveService.listMyLeaves());
    }

    @GetMapping("/pending")
    @PreAuthorize("@ss.hasPerm('module:leave:pending')")
    public Result<List<LeavePendingVo>> listMyPending() {
        return Result.success(leaveService.listPendingLeaves());
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("@ss.hasPerm('module:leave:approve')")
    public Result<?> approveLeave(@PathVariable Long id,
                                  @RequestBody @Valid LeaveApproveForm form) {
        return Result.judge(leaveService.approveLeave(id, form));
    }

    @GetMapping("/records")
    @PreAuthorize("@ss.hasPerm('module:leave:records')")
    public  Result<List<LeaveRecordVo>> getRecords() {
        return Result.success(leaveService.getRecords());
    }
}

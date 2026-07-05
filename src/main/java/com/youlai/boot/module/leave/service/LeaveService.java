package com.youlai.boot.module.leave.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.boot.module.leave.model.entity.LeaveRequest;
import com.youlai.boot.module.leave.model.form.LeaveForm;
import com.youlai.boot.module.leave.model.vo.LeaveLIstVo;
import com.youlai.boot.module.leave.model.vo.LeavePendingVo;

import java.util.List;

public interface LeaveService extends IService<LeaveRequest> {

    /**
     *
     * @param formData 新增请假对象
     * @return
     */
    boolean addLeave(LeaveForm formData);

    List<LeaveLIstVo> listMyLeaves();

    List<LeavePendingVo> listPendingLeaves();
}

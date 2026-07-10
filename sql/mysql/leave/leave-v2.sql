-- 1. 请假主表加「当前审批级」
ALTER TABLE leave_request
  ADD COLUMN current_step TINYINT NOT NULL DEFAULT 1
  COMMENT '当前审批级：1主管 2经理 0已结束';

-- 2. 审批记录表（每一级谁批的、结果是什么）
CREATE TABLE IF NOT EXISTS leave_approval_record (
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    leave_id     BIGINT NOT NULL COMMENT '请假单ID',
    step         TINYINT NOT NULL COMMENT '第几级：1主管 2经理',
    approver_id  BIGINT NOT NULL COMMENT '审批人',
    action       TINYINT NOT NULL COMMENT '1通过 2驳回',
    comment      VARCHAR(200) NULL COMMENT '驳回原因/备注',
    create_time  DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_leave_id (leave_id)
);

INSERT INTO sys_menu VALUES (2905, 290, '0,1,290', '审批流水', 'B', NULL, '', NULL, 'module:leave:records', NULL, NULL, 1, 4, '', NULL, now(), now(), NULL);

INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(2, 2905), (4, 2905), (5, 2905);

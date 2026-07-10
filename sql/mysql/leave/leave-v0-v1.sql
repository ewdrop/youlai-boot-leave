--新建请假申请表
CREATE TABLE leave_request (
	id 					BIGINT PRIMARY KEY AUTO_INCREMENT,
	user_id 		BIGINT NOT NULL COMMENT '申请人',
	start_time 	DATETIME NOT NULL,
	end_time 		DATETIME NOT NULL,
	reason 			VARCHAR(500) NOT NULL,
	status 			TINYINT NOT NULL DEFAULT 0 COMMENT '0待审批 1通过 2驳回',
	approver_id BIGINT NOT NULL,
	approve_time DATETIME NOT NULL,
	reject_reason VARCHAR(200) NULL,
	applicant_time DATETIME DEFAULT CURRENT_TIMESTAMP,
	is_deleted TINYINT DEFAULT 0
);

ALTER TABLE leave_request
  MODIFY COLUMN approver_id   BIGINT   NULL COMMENT '审批人',
  MODIFY COLUMN approve_time  DATETIME NULL COMMENT '批准时间';

--设置接口访问权限字段
INSERT INTO sys_menu VALUES (290, 1, '0,1', '请假管理', 'M', 'Leave', 'leave', 'module/leave/index', NULL, NULL, NULL, 1, 10, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (2901, 290, '0,1,290', '提交请假', 'B', NULL, '', NULL, 'module:leave:create', NULL, NULL, 1, 1, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (2902, 290, '0,1,290', '我的请假', 'B', NULL, '', NULL, 'module:leave:mine', NULL, NULL, 1, 2, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (2903, 290, '0,1,290', '待审批', 'B', NULL, '', NULL, 'module:leave:pending', NULL, NULL, 1, 3, '', NULL, now(), now(), NULL);
INSERT INTO sys_menu VALUES (2904, 290, '0,1,290', '审批操作', 'B', NULL, '', NULL, 'module:leave:approve', NULL, NULL, 1, 4, '', NULL, now(), now(), NULL);

SELECT id, name, perm FROM sys_menu WHERE perm LIKE 'module:leave:%';

-- 部门主管 role_id=4：pending + approve
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(4, 2901), (4, 2902), (4, 2903), (4, 2904);

-- 部门员工 role_id=5：只要 create + mine
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(5, 2901), (5, 2902);

DELETE FROM sys_role_menu  WHERE
menu_id IN (2901,2902) AND role_id = 6;

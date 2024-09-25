-- nib_assign_user: table
CREATE TABLE `nib_assign_user` (
                                   `id` bigint(20) NOT NULL COMMENT '主键 ID',
                                   `company_id` bigint(20) NOT NULL COMMENT '公司 ID',
                                   `project_id` bigint(20) DEFAULT NULL COMMENT '项目 ID',
                                   `business_id` bigint(20) NOT NULL COMMENT '业务记录 ID',
                                   `type` enum('DEMAND','MISSION','FLAW') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '业务类型, DEMAND=需求, MISSION=任务, FLAW=缺陷',
                                   `user_id` bigint(20) NOT NULL COMMENT '指派用户 ID',
                                   `deleted` tinyint(1) DEFAULT NULL COMMENT '是否删除，0=未删除，1=已删除',
                                   `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
                                   `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
                                   `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   KEY `idx_company_id` (`company_id`) USING BTREE,
                                   KEY `idx_project_id` (`project_id`) USING BTREE,
                                   KEY `idx_business_id` (`business_id`) USING BTREE,
                                   KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='分配用户表';

-- No native definition for element: idx_company_id (index)

-- No native definition for element: idx_project_id (index)

-- No native definition for element: idx_business_id (index)

-- No native definition for element: idx_user_id (index)

-- nib_comment: table
CREATE TABLE `nib_comment` (
                               `id` bigint(20) NOT NULL COMMENT '主键 ID',
                               `company_id` bigint(20) NOT NULL COMMENT '公司 ID',
                               `project_id` bigint(20) NOT NULL COMMENT '项目 ID',
                               `type` enum('DEMAND_COMMENT','MISSION_COMMENT','FLAW_COMMENT') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型, DEMAND_COMMENT=需求下评论, MISSION_COMMENT=任务下评论, FLAW_COMMENT=缺陷下评论',
                               `business_id` bigint(20) NOT NULL COMMENT '被回复的业务 ID(需求 ID、任务 ID、缺陷 ID)',
                               `root_comment_id` bigint(20) DEFAULT NULL COMMENT '根评论 ID',
                               `replied_comment_id` bigint(20) DEFAULT NULL COMMENT '被回复的具体评论 ID',
                               `child_comment` tinyint(1) DEFAULT '0' COMMENT '是否有子评论，0=没有，1=有',
                               `deleted` tinyint(1) DEFAULT NULL COMMENT '是否删除，0=未删除，1=已删除',
                               `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
                               `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
                               `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                               PRIMARY KEY (`id`) USING BTREE,
                               KEY `idx_business_id` (`business_id`) USING BTREE,
                               KEY `idx_replied_comment_id` (`replied_comment_id`) USING BTREE,
                               KEY `idx_company_id` (`company_id`) USING BTREE,
                               KEY `idx_project_id` (`project_id`) USING BTREE,
                               KEY `idx_root_comment_id` (`root_comment_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='评论回复表';

-- No native definition for element: idx_company_id (index)

-- No native definition for element: idx_project_id (index)

-- No native definition for element: idx_business_id (index)

-- No native definition for element: idx_root_comment_id (index)

-- No native definition for element: idx_replied_comment_id (index)

-- nib_company: table
CREATE TABLE `nib_company` (
                               `id` bigint(20) NOT NULL COMMENT '主键 ID',
                               `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '公司名称',
                               `logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '公司 logo',
                               `deleted` tinyint(1) DEFAULT NULL COMMENT '是否删除，0=未删除，1=已删除',
                               `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
                               `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
                               `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                               PRIMARY KEY (`id`) USING BTREE,
                               KEY `idx_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='公司表';

-- No native definition for element: idx_name (index)

-- nib_content: table
CREATE TABLE `nib_content` (
                               `id` bigint(20) NOT NULL COMMENT '主键 ID',
                               `business_id` bigint(20) DEFAULT NULL COMMENT '业务记录 ID',
                               `type` enum('DEMAND','MISSION','FLAW','COMMENT') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '类型, DEMAND=需求, MISSION=任务, FLAW=缺陷, COMMENT=评论',
                               `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '文本内容',
                               `deleted` tinyint(1) DEFAULT NULL COMMENT '是否删除，0=未删除，1=已删除',
                               `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
                               `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
                               `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                               PRIMARY KEY (`id`) USING BTREE,
                               KEY `idx_business_id` (`business_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='内容表';

-- No native definition for element: idx_business_id (index)

-- nib_demand: table
CREATE TABLE `nib_demand` (
                              `id` bigint(20) NOT NULL COMMENT '主键 ID',
                              `company_id` bigint(20) NOT NULL COMMENT '公司 ID',
                              `project_id` bigint(20) NOT NULL COMMENT '项目 ID',
                              `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '需求标题',
                              `priority` enum('HIGH','MIDDLE','LOW') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '优先级, HIGH=高, MIDDLE=中, LOW=低',
                              `state` enum('PLANNING','DEMAND_COMPLETE','APPRAISAL','DEVELOPING','SUBMIT_TEST','TESTING','TEST_PASS','ONLINE','PRODUCT_ACCEPTING','PRODUCT_ACCEPTED','REFUSE') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'PLANNING' COMMENT '需求状态, PLANNING=规划中, DEMAND_COMPLETE=需求方案完成, APPRAISAL=已评审, DEVELOPING=开发中, SUBMIT_TEST=已提测, TESTING=测试中, TEST_PASS=测试通过, ONLINE=已上线, PRODUCT_ACCEPTING=产品验收中, PRODUCT_ACCEPTED=产品验收通过, REFUSE=已拒绝',
                              `start_time` datetime DEFAULT NULL COMMENT '开始时间',
                              `end_time` datetime DEFAULT NULL COMMENT '结束时间',
                              `deleted` tinyint(1) DEFAULT NULL COMMENT '是否删除，0=未删除，1=已删除',
                              `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
                              `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
                              `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                              PRIMARY KEY (`id`) USING BTREE,
                              KEY `idx_project_id` (`project_id`) USING BTREE,
                              KEY `idx_title` (`title`) USING BTREE,
                              KEY `idx_start_time` (`start_time`) USING BTREE,
                              KEY `idx_end_time` (`end_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='需求表';

-- No native definition for element: idx_project_id (index)

-- No native definition for element: idx_title (index)

-- No native definition for element: idx_start_time (index)

-- No native definition for element: idx_end_time (index)

-- nib_flaw: table
CREATE TABLE `nib_flaw` (
                            `id` bigint(20) NOT NULL COMMENT '主键 ID',
                            `company_id` bigint(20) NOT NULL COMMENT '公司 ID',
                            `project_id` bigint(20) NOT NULL COMMENT '项目 ID',
                            `demand_id` bigint(20) DEFAULT NULL COMMENT '需求 ID',
                            `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '缺陷标题',
                            `type` enum('DATA','CODE','UNFINISH_DEMAND','DESIGN','COMPATIBILITY','INTERFACE_OPTIMIZATION','PERFORMANCE') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '类型, DATA=数据问题, CODE=代码问题, UNFINISH_DEMAND=未完成需求, DESIGN=设计缺陷, COMPATIBILITY=兼容问题, INTERFACE_OPTIMIZATION=界面优化, PERFORMANCE=性能问题',
                            `priority` enum('HIGH','MIDDLE','LOW') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '优先级, HIGH=高, MIDDLE=中, LOW=低',
                            `state` enum('NEW','PROCESSING','PROCESSED','REFUSE','CLOSE','REOPEN') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'NEW' COMMENT '状态, NEW=新缺陷, PROCESSING=处理中, PROCESSED=处理完成, CLOSE=关闭, REFUSE=已拒绝',
                            `start_time` datetime DEFAULT NULL COMMENT '开始时间',
                            `end_time` datetime DEFAULT NULL COMMENT '结束时间',
                            `deleted` tinyint(1) DEFAULT NULL COMMENT '是否删除，0=未删除，1=已删除',
                            `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
                            `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`) USING BTREE,
                            KEY `idx_company_id` (`company_id`) USING BTREE,
                            KEY `idx_project_id` (`project_id`) USING BTREE,
                            KEY `idx_title` (`title`) USING BTREE,
                            KEY `idx_start_time` (`start_time`) USING BTREE,
                            KEY `idx_end_time` (`end_time`) USING BTREE,
                            KEY `demand_id` (`demand_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='缺陷表';

-- No native definition for element: idx_company_id (index)

-- No native definition for element: idx_project_id (index)

-- No native definition for element: demand_id (index)

-- No native definition for element: idx_title (index)

-- No native definition for element: idx_start_time (index)

-- No native definition for element: idx_end_time (index)

-- nib_mission: table
CREATE TABLE `nib_mission` (
                               `id` bigint(20) NOT NULL COMMENT '主键 ID',
                               `company_id` bigint(20) NOT NULL COMMENT '公司 ID',
                               `project_id` bigint(20) NOT NULL COMMENT '项目 ID',
                               `demand_id` bigint(20) DEFAULT NULL COMMENT '需求 ID',
                               `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务标题',
                               `priority` enum('HIGH','MIDDLE','LOW') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '优先级, HIGH=高, MIDDLE=中, LOW=低',
                               `state` enum('NOT_PROCESS','PROCESSING','PROCESSED','REFUSE') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '状态, NOT_PROCESS=未处理, PROCESSING=处理中, PROCESSED=处理完成, REFUSE=已拒绝',
                               `start_time` datetime DEFAULT NULL COMMENT '开始时间',
                               `end_time` datetime DEFAULT NULL COMMENT '结束时间',
                               `deleted` tinyint(1) DEFAULT NULL COMMENT '是否删除，0=未删除，1=已删除',
                               `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
                               `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
                               `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                               PRIMARY KEY (`id`) USING BTREE,
                               KEY `idx_company_id` (`company_id`) USING BTREE,
                               KEY `idx_project_id` (`project_id`) USING BTREE,
                               KEY `idx_title` (`title`) USING BTREE,
                               KEY `idx_start_time` (`start_time`) USING BTREE,
                               KEY `idx_end_time` (`end_time`) USING BTREE,
                               KEY `idx_demand_id` (`demand_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='任务表';

-- No native definition for element: idx_company_id (index)

-- No native definition for element: idx_project_id (index)

-- No native definition for element: idx_demand_id (index)

-- No native definition for element: idx_title (index)

-- No native definition for element: idx_start_time (index)

-- No native definition for element: idx_end_time (index)

-- nib_operate_record: table
CREATE TABLE `nib_operate_record` (
                                      `id` bigint(20) NOT NULL COMMENT '主键 ID',
                                      `company_id` bigint(20) NOT NULL COMMENT '公司 ID',
                                      `project_id` bigint(20) DEFAULT NULL COMMENT '项目 ID',
                                      `business_id` bigint(20) NOT NULL COMMENT '业务记录 ID',
                                      `type` enum('DEMAND','MISSION','FLAW') COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '业务类型, DEMAND=需求, MISSION=任务, FLAW=缺陷',
                                      `user_id` bigint(20) NOT NULL COMMENT '用户 ID',
                                      `content` varchar(1024) COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作内容',
                                      `deleted` tinyint(1) DEFAULT NULL COMMENT '是否删除，0=未删除，1=已删除',
                                      `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
                                      `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
                                      `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                      PRIMARY KEY (`id`),
                                      KEY `idx_business_id` (`business_id`),
                                      KEY `idx_company_id` (`company_id`),
                                      KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='操作记录表';

-- No native definition for element: idx_company_id (index)

-- No native definition for element: idx_business_id (index)

-- No native definition for element: idx_user_id (index)

-- nib_project: table
CREATE TABLE `nib_project` (
                               `id` bigint(20) NOT NULL COMMENT '主键 ID',
                               `company_id` bigint(20) DEFAULT NULL COMMENT '公司 ID',
                               `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '项目名称',
                               `logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '项目 logo',
                               `deleted` tinyint(1) DEFAULT NULL COMMENT '是否删除，0=未删除，1=已删除',
                               `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
                               `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
                               `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                               PRIMARY KEY (`id`) USING BTREE,
                               KEY `idx_name` (`name`) USING BTREE,
                               KEY `idx_company_id` (`company_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='项目表';

-- No native definition for element: idx_company_id (index)

-- No native definition for element: idx_name (index)

-- nib_role: table
CREATE TABLE `nib_role` (
                            `id` bigint(20) NOT NULL COMMENT '主键 ID',
                            `user_id` bigint(20) NOT NULL COMMENT '用户 ID',
                            `company_id` bigint(20) DEFAULT NULL COMMENT '公司 ID',
                            `role` enum('SYSTEM_ADMIN','COMPANY_ADMIN','COMPANY_MEMBER') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色, SYSTEM_ADMIN=系统管理员, COMPANY_ADMIN=公司管理员, COMPANY_MEMBER=公司普通成员',
                            `deleted` tinyint(1) DEFAULT NULL COMMENT '是否删除，0=未删除，1=已删除',
                            `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
                            `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`) USING BTREE,
                            KEY `idx_user_id` (`user_id`) USING BTREE,
                            KEY `idx_company_id` (`company_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户角色表';

-- No native definition for element: idx_user_id (index)

-- No native definition for element: idx_company_id (index)

-- nib_user: table
CREATE TABLE `nib_user` (
                            `id` bigint(20) NOT NULL COMMENT '主键 ID',
                            `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户名称',
                            `mobile` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号',
                            `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
                            `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码',
                            `salt` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '盐值',
                            `deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除，0=未删除，1=已删除',
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`) USING BTREE,
                            UNIQUE KEY `uk_mobile` (`mobile`) USING BTREE,
                            UNIQUE KEY `uk_email` (`email`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户表';


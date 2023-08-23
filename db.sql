# 建表语句
CREATE TABLE `nib_user` (
                            `id` bigint(20) NOT NULL COMMENT '主键 ID',
                            `user_name` varchar(64) DEFAULT NULL COMMENT '用户名称',
                            `mobile` varchar(16) DEFAULT NULL COMMENT '手机号',
                            `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
                            `password` varchar(64) DEFAULT NULL COMMENT '密码',
                            `deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除，0=未删除，1=已删除',
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`) USING BTREE,
                            UNIQUE KEY `uk_mobile` (`mobile`) USING BTREE,
                            UNIQUE KEY `uk_email` (`email`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
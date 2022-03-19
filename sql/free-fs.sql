CREATE TABLE `mooc_backend_user_t` (
    `UUID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键编号',
    `user_name` varchar(50) DEFAULT NULL COMMENT '用户账号',
    `user_pwd` varchar(50) DEFAULT NULL COMMENT '用户密码',
    `user_phone` varchar(50) DEFAULT NULL COMMENT '用户手机号',
    PRIMARY KEY (`UUID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='后台用户表';



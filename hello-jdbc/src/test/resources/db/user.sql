DROP TABLE
    IF
    EXISTS `user`;
CREATE TABLE `user` (
                        `id` BIGINT ( 20 ) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                        `name` VARCHAR ( 32 ) DEFAULT NULL COMMENT '姓名',
                        `age` INT ( 11 ) DEFAULT NULL COMMENT '年龄',
                        `email` VARCHAR ( 50 ) DEFAULT NULL COMMENT '邮箱',
                        `create_time` datetime DEFAULT NULL,
                        `update_time` datetime DEFAULT NULL,
                        `version` INT ( 2 ) DEFAULT '1',
                        `status` INT ( 11 ) DEFAULT '1',
                        `deleted` INT ( 11 ) DEFAULT '0',
                        PRIMARY KEY ( `id` )
) ENGINE = INNODB DEFAULT CHARSET = utf8;
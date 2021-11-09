package com.leon.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @PROJECT_NAME: hello-transaction
 * @CLASS_NAME: DataSource
 * @AUTHOR: OceanLeonAI
 * @CREATED_DATE: 2021/9/17 19:37
 * @Version 1.0
 * @DESCRIPTION:
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DataSource implements Serializable {

    private static final long serialVersionUID = 5644431185372689899L;

    /**
     * id
     */
    private Long id;

    /**
     * 数据源名称
     */
    private String name;

    /**
     * 主机ip
     */
    private String hostName;

    /**
     * 端口
     */
    private String port;

    /**
     * 数据库名称
     */
    private String databaseName;

    /**
     * 数据源md5码
     */
    private String md5Code;

}

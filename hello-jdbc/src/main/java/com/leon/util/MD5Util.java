package com.leon.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @PROJECT_NAME: dmp-metadata
 * @CLASS_NAME: MD5Util
 * @AUTHOR: OceanLeonAI
 * @CREATED_DATE: 2021/9/17 16:14
 * @Version 1.0
 * @DESCRIPTION:
 **/
public class MD5Util {

    /**
     * 工具类，私有化构造，防止外部实例化对象
     */
    private MD5Util() {
    }

    /**
     * MD5 消息摘要
     *
     * @param data
     * @return
     */
    public static byte[] encodeMD5(String data) {
        // 执行消息摘要
        byte[] bytes = DigestUtils.md5(data);
        return bytes;
    }

    /**
     * MD5 消息摘要
     *
     * @param data
     * @return
     */
    public static String encodeMD5Hex(String data) {
        // 执行消息摘要
        String md5Hex = DigestUtils.md5Hex(data);
        return md5Hex;
    }

//    public static void main(String[] args) {
//        // IP/PORT/DBNAME
//        String datasourceName = "oracle_test11";
//        String hostName = "192.168.11.232";
//        String port = "1521";
//        String databaseName = "zhouchun";
//        String username = "zhouchun";
//        String password = "zhouchun";
//        String splitStr = "/";
//        String str = hostName + splitStr + port + splitStr + username + splitStr + password;
//        System.out.println("str --->" + str);
//
//        String md5Hex = DigestUtils.md5Hex(str.getBytes());
//        System.out.println("md5Hex --->" + md5Hex);
//        System.out.println("md5Hex length --->" + md5Hex.length());
//        System.out.println(" bit length --->" + md5Hex.length() * 4);
//    }
}

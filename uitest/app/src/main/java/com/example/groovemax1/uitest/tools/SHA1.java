package com.example.groovemax1.uitest.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 文件名：
 * 描述：加密
 * 作者：
 * 时间：
 */
public class SHA1 {

    public static String getSHA1(String val) throws NoSuchAlgorithmException {

        MessageDigest md5 = MessageDigest.getInstance("SHA-1");
        md5.update(val.getBytes());
        byte[] m = md5.digest();//加密
        return getString(m);
    }

    private static String getString(byte[] b){
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < b.length; i ++){
            sb.append(b[i]);
        }
        return sb.toString();
    }

}

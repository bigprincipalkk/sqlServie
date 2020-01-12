package com.youngc.pipeline.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @name
 * @description
 * @author: ZhangWenWei
 * @create: 2018/5/9 下午2:15
 **/
public class EncryptUtil {
    public static String encodeMD5(String str){

        String reStr = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(str.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bytes){
                int bt = b & 0xff;
                if (bt < 16){
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(bt));
            }
            reStr = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return reStr;
    }
}

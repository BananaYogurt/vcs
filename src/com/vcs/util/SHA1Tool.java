package com.vcs.util;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * created by 吴震煌 on 2017/3/17 8:40
 */

/**
 * 用SHA1来获得文件的数字签名，判断文件是否更改过
 */
public class SHA1Tool {

    public static String encrypt(byte[] src) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] data = digest.digest(src);
        StringBuffer hexbuffer = new StringBuffer();
        for(int i = 0 ;i < data.length;i++){
            String hexString = Integer.toHexString(data[i] & 0xFF);
            if(hexString.length() < 2)
                hexbuffer.append(0);
            hexbuffer.append(hexString);
        }
        return hexbuffer.toString();
    }

    /**
     * 将文件用SHA1算法转成40位数的数字签名
     * @param file
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static String encrypt(File file) throws NoSuchAlgorithmException, IOException {

        long start = System.currentTimeMillis();

        byte[] src = FileToByte.fileToByte(file);

        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] data = digest.digest(src);
        StringBuffer hexbuffer = new StringBuffer();
        for(int i = 0 ;i < data.length;i++){
            String hexString = Integer.toHexString(data[i] & 0xFF);
            if(hexString.length() < 2)
                hexbuffer.append(0);
            hexbuffer.append(hexString);
        }

        long end = System.currentTimeMillis();
        System.out.println("【com.vcs.util.SHA1Tool】log : 计算数字签名耗费了"+(end - start)+"毫秒");

        return hexbuffer.toString();
    }

}

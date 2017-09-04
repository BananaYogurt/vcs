package com.vcs.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * created by wuzh on 2017/5/3 9:10
 */

/**
 * 这个是将文件转成字节数组的工具类
 */
public class FileToByte {

    public FileToByte(File file) {

    }

    //将文件转换成字节数组
    public static byte[] fileToByte(File file) throws IOException{

        byte[] src = null;
        try{
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(2000);
            byte[] bytes = new byte[2000];
            int n;
            while ((n = fis.read(bytes)) != -1) {
                bos.write(bytes, 0, n);
            }
            fis.close();
            bos.close();
            src = bos.toByteArray();
        }catch (IOException ie){
            ie.printStackTrace();
        }
        return src;
    }

}

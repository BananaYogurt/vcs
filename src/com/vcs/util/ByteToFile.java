package com.vcs.util;

import java.io.*;

/**
 * created by wuzh on 2017/5/3 9:46
 */
public class ByteToFile {

    /**
     * 将byte[]转换成File
     * @param src
     * @return
     * @throws IOException
     */
    public static File byteToFile(byte[] src,String fileName) throws IOException{

        File file = new File(fileName);

        String parent = file.getParent();

        File pfile = new File(parent);

        //判断当前文件的父目录是否存在，如果不存在，就创建
        if( !pfile.exists() )
            pfile.mkdirs();

        //file.createNewFile();

        FileOutputStream fos = new FileOutputStream(file);

        BufferedOutputStream bos = new BufferedOutputStream(fos);

        bos.write(src);

        bos.close();

        fos.close();

        return file;
    }
}

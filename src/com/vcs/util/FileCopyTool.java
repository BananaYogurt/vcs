package com.vcs.util;

import java.io.*;

/**
 * created by wuzh on 2017/5/4 16:31
 */
public class FileCopyTool {

    /**
     * 将文件f1的内容拷贝到文件f2
     * @param f1
     * @param f2
     * @throws IOException
     */
    public static void fileCopy(File f1, File f2)throws IOException{
        InputStream input = new FileInputStream(f1);
        OutputStream out = new FileOutputStream(f2);
        byte[] buffer = new byte[1024];
        int line ;
        while((line = input.read(buffer)) != -1){
            out.write(buffer,0,line);
        }
        input.close();
        out.close();
    }

}

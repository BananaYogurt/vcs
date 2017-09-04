package com.vcs.util;

import java.io.*;

/**
 * created by wuzh on 2017/4/25 15:37
 */
public class FileToString {

    /**
     * 用来将File转换成String，方便之后对比文件的差异
     * @param file
     * @return
     * @throws IOException
     */
    public static String toString(File file) throws IOException {

        InputStream in = new FileInputStream(file);

        BufferedReader reader  =  new BufferedReader(new InputStreamReader(in));

        StringBuffer sb = new StringBuffer();

        String line;

        while((line = reader.readLine()) != null){
            sb.append(line);
            sb.append("\n");
        }

        return sb.toString();
    }
}

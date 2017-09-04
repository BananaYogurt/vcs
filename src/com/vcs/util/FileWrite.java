package com.vcs.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * created by wuzh on 2017/5/10 8:33
 */
public class FileWrite {

    public static void write(File file,String content) throws IOException {

        FileWriter fw = new FileWriter(file,true);

        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(content);

        bw.close();

        fw.close();

    }
}

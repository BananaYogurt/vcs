package com.vcs.algorithm;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * created by wuzh on 2017/4/27 19:47
 */
public class FileToHashList {

    /**
     * 用来获得每一行的hashCode
     * @param file
     * @return
     * @throws IOException
     */
    public static List<LineHashCode> hashList(File file) throws IOException {

        int lineNumber = 0;

        List<LineHashCode> hashList = new LinkedList<>();

        InputStream in = new FileInputStream(file);

        BufferedReader reader  =  new BufferedReader(new InputStreamReader(in));

        String line;

        LineHashCode lhc;

        while((line = reader.readLine()) != null){

            lhc = new LineHashCode(line);

            lhc.setLineNumber(lineNumber);

            lineNumber++;

            hashList.add(lhc);
        }

        reader.close();

        in.close();

        return hashList;
    }
}

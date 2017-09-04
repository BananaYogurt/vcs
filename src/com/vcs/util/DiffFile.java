package com.vcs.util;

import com.vcs.algorithm.DiffMatchPatch;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * created by wuzh on 2017/4/25 22:27
 */
public class DiffFile {

    /**
     * 将两个文本的差异计算出来 返回一个差异的list
     * @param fileName1
     * @param fileName2
     * @return
     * @throws IOException
     */
    public static List<DiffMatchPatch.Diff> diffFile(String fileName1,String fileName2) throws IOException {

       File f1 = new File(fileName1);

       File f2 = new File(fileName2);

        DiffMatchPatch dmp = new DiffMatchPatch();

        String sf1 = FileToString.toString(f1);

        String sf2 = FileToString.toString(f2);

        return dmp.diff_main(sf1,sf2,false);
    }
}

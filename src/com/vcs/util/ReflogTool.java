package com.vcs.util;

import com.vcs.components.Reflog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * created by wuzh on 2017/5/10 9:43
 */
public class ReflogTool {

    public static void updateReflog(File reflogFile ,String content)throws IOException{

        FileWrite.write(reflogFile,content);
    }

    public static void updateReflog(String content)throws IOException{

        FileWrite.write(getReflog(),content);
    }

    public static File getReflog()throws IOException{

        //要先知道当前的分支名称,分支的路径
        VcsConfigParameter parameter = new VcsConfigParameter(Env.vcsConfig);

        String prefix = parameter.getLogs();

        String branch = Env.branch;

        String name = branch.substring(1,branch.length()-1);

        String reflogPath = prefix+name+".reflog";

        File reflog = new File(reflogPath);

        return reflog;

    }

    public static List<Reflog> getReflogList()throws IOException{

        File reflogFile = getReflog();

        List list = new ArrayList();

        FileReader fr = new FileReader(reflogFile);

        BufferedReader br = new BufferedReader(fr);

        int prefixPoint;

        int suffixPoint;

        String line;


        while ((line = br.readLine())!= null){

            prefixPoint = line.indexOf("@");

            suffixPoint = line.lastIndexOf("@");

            String versionId = line.substring(0,prefixPoint);

            String message = line.substring(prefixPoint+1,suffixPoint);

            String time = line.substring(suffixPoint+1);

            Reflog reflog = new Reflog(versionId,message,time);

            list.add(reflog);
        }

        return list;
    }
}

package com.vcs.util;

import com.vcs.components.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * created by wuzh on 2017/5/10 9:41
 */
public class LogTool {

    public static void updateLog(File logFile ,String content) throws IOException {

        FileWrite.write(logFile,content);
    }

    public static void updateLog(String content)throws IOException{

        FileWrite.write(getLog(),content);
    }

    public static File getLog()throws IOException{

        //要先知道当前的分支名称,分支的路径
        VcsConfigParameter parameter = new VcsConfigParameter(Env.vcsConfig);

        String prefix = parameter.getLogs();

        String branch = Env.branch;

        String name = branch.substring(1,branch.length()-1);

        String logPath = prefix+name+".log";

        File log = new File(logPath);

        return log;

    }

    public static List<Log> getLogList()throws IOException{

        File logFile = getLog();

        List list = new ArrayList();

        FileReader fr = new FileReader(logFile);

        BufferedReader br = new BufferedReader(fr);

        String line;

        int prefixPoint;

        int suffixPoint;

        while ((line = br.readLine())!= null){

            prefixPoint = line.indexOf("@");

            suffixPoint = line.lastIndexOf("@");

            String versionId = line.substring(0,prefixPoint);

            String message = line.substring(prefixPoint+1,suffixPoint);

            String time = line.substring(suffixPoint+1);

            Log log = new Log(versionId,message,time);

            list.add(log);

        }

        return list;

    }

    //public static

    public static void main(String[] args)throws IOException {
        String s = "F6YSR6HU17@ab@c@2017-05-10 10:37:48";
        int i = s.indexOf("@");
        int j = s.lastIndexOf("@");
        System.out.println(s.substring(0,i));
        System.out.println(s.substring(j+1));
        System.out.println(s.substring(i+1,j));
    }
}

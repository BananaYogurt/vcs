package com.vcs.command;

import com.vcs.util.Env;
import com.vcs.util.Result;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * created by 吴震煌 on 2017/3/8 10:28
 */
public class Ls {

    private String fileName ;
    private File file;
    private Result result = new Result();
    private static final String EMPTY_MSG = "该文件夹下面为空!";
    private static final String ERROR_MSG = "命令的参数不正确!";
    private StringBuilder sb ;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public Result execute(String input){
        String[] s = input.split(" ");
        if(s.length == 1){
            fileName = Env.path;
            file = new File(fileName);
            if(file.exists()){
                sb = new StringBuilder();
                sb.append("文件(夹)名\t\t修改时间\n");
                File[] files = file.listFiles();
                if(files.length == 0){
                    result.setOutput(EMPTY_MSG);
                    return result;
                }
                for(File f : files){

                    if(f.isDirectory())
                        sb.append(f.getName()+"\\"+"\t\t").append(format(f.lastModified())+"\n");
                    else
                        sb.append(f.getName()+"\t\t").append(format(f.lastModified())+"\n");
                }
                result.setOutput(sb.toString());
                return result;
            }
            result.setOutput(EMPTY_MSG);
            return result;
        }
        result.setMessage(ERROR_MSG);
        return result;

    }

    public String format(long t){
        return sdf.format(new Date(t));
    }
}

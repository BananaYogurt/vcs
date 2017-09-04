package com.vcs.command;

import com.vcs.util.Env;
import com.vcs.util.Result;

import java.io.File;

/**
 * created by 吴震煌 on 2017/3/13 15:10
 */
public class Mkdir {

    private static final String ERROR_EXISTS = "该文件夹已经存在！";

    private static final String ERROR_MSG = "命令的参数不正确！";

    private static final String ERROR_EMPTY = "命令没有指定参数！";

    private Result result;

    public Result execute(String input){
        result = new Result();
        String[] param = input.split(" ");
        if(param.length > 2){
            result.setMessage(ERROR_MSG);
            return  result;
        }else if(param.length == 1){
            result.setMessage(ERROR_EMPTY);
            return result;
        }else {
            String dir = param[1];
            String dirName = Env.path + "\\"+ dir;
            File file = new File(dirName);
            if(!file.exists()){
                file.mkdirs();
                result.setError(false);
            }else{
                result.setMessage(ERROR_EXISTS);
            }
            return result;
        }
    }
}

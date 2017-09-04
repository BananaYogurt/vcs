package com.vcs.command;

import com.vcs.util.Env;
import com.vcs.util.Result;
import com.vcs.command.inf.BaseCommandImpl;

import java.io.File;
import java.io.IOException;

/**
 * created by 吴震煌 on 2017/3/13 15:47
 */
public class Touch extends BaseCommandImpl {

    private static final String ERROR_MSG = "输入的命令参数不正确！";
    private static final String ERROR_EXISTS = "文件已经存在！";
    private static final String ERROR_UNKNOWN = "创建文件时候出现未知错误！";

    public Result execute(String input){
        param = input.split(" ");
        if(param.length == 2){
            String fileName = Env.path+"\\"+param[1];
            File file = new File(fileName);
            if(!file.exists()){
                try {
                    file.createNewFile();
                    result.setError(false);
                } catch (IOException e) {
                    result.setMessage(ERROR_UNKNOWN);
                    return result;
                }
            }else {
                result.setMessage(ERROR_EXISTS);
            }
        }else{
            result.setMessage(ERROR_MSG);
        }
        return result;
    }
}

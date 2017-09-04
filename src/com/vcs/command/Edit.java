package com.vcs.command;

import com.vcs.command.inf.BaseCommandImpl;
import com.vcs.util.Env;
import com.vcs.util.Result;

import java.io.File;
import java.io.IOException;

/**
 * created by wuzh on 2017/5/10 12:26
 */
public class Edit extends BaseCommandImpl{

    @Override
    public Result execute(String input) {

        String[] s = input.split(" ");

        if(s.length == 1){
            result.setMessage(" edit命令缺少要编辑的文件名,edit + [fileNme]");
            return result;
        }

        if(s.length == 2){
            String parent = Env.path;
            String name = s[1];
            String fileName = parent+"\\"+name;
            File target = new File(fileName);
            if(!target.exists()){
                result.setMessage(" 当前路径下不存在<<"+name+">>");
                return result;
            }else{
                if(target.isDirectory()){
                    result.setMessage("'"+name+"'是文件夹,不能用记事本打开");
                    return result;
                }
                if(target.isFile()){
                    Runtime rt = Runtime.getRuntime();
                    try {
                        rt.exec("notepad.exe "+fileName);
                        result.setError(false);
                        return result;
                    } catch (IOException ie) {
                        System.out.println(ie.getMessage());
                        result.setMessage(ie.getMessage());
                        return result;
                    }
                }
            }
        }

        result.setMessage(" edit命令格式错误,edit + [fileNme]");

        return result;
    }
}

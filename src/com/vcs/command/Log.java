package com.vcs.command;

import com.vcs.command.inf.BaseCommandImpl;
import com.vcs.util.DirectoryTool;
import com.vcs.util.Env;
import com.vcs.util.LogTool;
import com.vcs.util.Result;

import java.io.IOException;
import java.util.List;

/**
 * created by wuzh on 2017/5/10 9:10
 */
public class Log extends BaseCommandImpl{

    @Override
    public Result execute(String input) {

        try {
            if(!DirectoryTool.isDepository(Env.path)){
                result.setMessage("当前不在仓库路径!无法查看提交日志");
                return result;
            }
        }catch (IOException e){
            result.setMessage(e.getMessage());
            return result;
        }

        if(!input.equals("log")){
            result.setMessage("log命令格式不正确");
            return result;
        }

        StringBuilder sb = new StringBuilder();

        try {
            List<com.vcs.components.Log> list = LogTool.getLogList();
            for(int i = 0; i < list.size();i++){
                com.vcs.components.Log commitLog = list.get(i);
                sb.append("{").append(i).append("}  ").append(commitLog.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        result.setOutput(sb.toString());
        return result;
    }
}

package com.vcs.command;

import com.vcs.command.inf.BaseCommandImpl;
import com.vcs.util.*;

import java.io.IOException;
import java.util.List;

/**
 * created by wuzh on 2017/5/10 9:11
 */
public class Reflog extends BaseCommandImpl{

    @Override
    public Result execute(String input) {

        String[] s = input.split(" ");

        try {
            if(!DirectoryTool.isDepository(Env.path)){
                result.setMessage(" 当前不在仓库路径!无法查看版本变化日志");
                return result;
            }
        }catch (IOException e){
            result.setMessage(e.getMessage());
            return result;
        }

        if(!input.equals("reflog")){
            result.setMessage("reflog命令格式不正确");
            return result;
        }

        StringBuilder sb = new StringBuilder();
        int count = 0;

        try {
            List<com.vcs.components.Reflog> list = ReflogTool.getReflogList();
            for(int i = list.size() - 1;i >= 0;i--){
                com.vcs.components.Reflog flog = list.get(i);
                sb.append(flog.getVersionId()).append("  ").append("HEAD@{").append(count).append("}:");
                sb.append(flog.getMessage()).append("   at  ").append(flog.getTime()).append("\n");
                count++;
            }

        } catch (IOException e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            return result;
        }

        result.setOutput(sb.toString());
        return result;
    }
}

package com.vcs.command;

import com.vcs.command.inf.BaseCommandImpl;
import com.vcs.util.BranchTool;
import com.vcs.util.DirectoryTool;
import com.vcs.util.Env;
import com.vcs.util.Result;
import org.dom4j.DocumentException;

import java.io.IOException;

/**
 * created by wuzh on 2017/4/29 19:03
 */
public class NewBranch extends BaseCommandImpl {

    private static final String ERROR_MSG = "输入的命令参数不正确!";
    private static final String ERROR_EMPTY = "缺少分支名!";
    private static final String ERROR_EXISTS = "分支已经存在!";
    private static final String ERROR_NODIR = "当前不在仓库路径,无法创建新的分支！";

    @Override
    public Result execute(String input) {

        //不在仓库路径下
        try {
            if(!DirectoryTool.isDepository(Env.path)){
                result.setMessage(ERROR_NODIR);
                return result;
            }

        }catch (IOException e){}
        param = input.split(" ");
        if(param.length == 2){
            //要创建的分支名
            String branch =  param[1];
            try {
                //这个分支名次已经存在
                if(BranchTool.loadBranchName().contains(branch)){
                    result.setMessage(ERROR_EXISTS);
                }else{//可以正常创建新的分支
                    if(BranchTool.addBranch(branch)){
                        result.setError(false);
                        return result;
                    }
                }
            }catch (DocumentException de){
                result.setMessage(de.getMessage());
            }

        }else if(param.length == 1){
            result.setMessage(ERROR_EMPTY);
        }else{
            result.setMessage(ERROR_MSG);
        }
        return result;
    }


}

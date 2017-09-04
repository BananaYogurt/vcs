package com.vcs.command;

import com.vcs.command.inf.BaseCommandImpl;
import com.vcs.util.BranchTool;
import com.vcs.util.DirectoryTool;
import com.vcs.util.Env;
import com.vcs.util.Result;
import org.dom4j.DocumentException;

import java.io.IOException;

/**
 * created by wuzh on 2017/4/29 19:42
 */
public class Checkout extends BaseCommandImpl {

    private static final String ERROR_NODIR = "当前不在仓库路径,无法切换分支！";

    private static final String ERROR_MSG = "输入的命令参数不正确!";

    @Override
    public Result execute(String input) {
        //不在仓库路径下
        try {
            if(!DirectoryTool.isDepository(Env.path)){
                result.setMessage(ERROR_NODIR);
                return result;
            }

        }catch (IOException e){
            result.setMessage(e.getMessage());
            return result;
        }

        param = input.split(" ");
        String branch = param[1];
        if(param.length == 2){
            /*if(("("+branch+")").equals(Env.branch)){
                result.setMessage("当前已经是在【"+branch+"】分支下");
                return result;
            }*/
            try {
                if(BranchTool.loadBranchName().contains(branch)){
                    Env.branch = "("+branch+")";
                    //更新当前显示前缀
                    Env.prefix = "\n"+Env.USER_NAME+"@"+Env.COMPUTER_NAME+" "+Env.path+" "+Env.branch+"\n ";
                    result.setError(false);
                    try {
                        BranchTool.checkout(branch,true);
                    }catch (Exception e){
                        result.setMessage(e.getMessage());
                        return result;
                    }
                    return result;
                }else{
                    result.setMessage("版本库没有【"+branch+"】分支");
                    return result;
                }
            }catch (DocumentException de){
                result.setMessage(de.getMessage());
                return result;
            }
        }else{
            result.setMessage(ERROR_MSG);
            return result;
        }

    }
}

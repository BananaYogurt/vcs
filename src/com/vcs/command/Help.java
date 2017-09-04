package com.vcs.command;

import com.vcs.command.inf.BaseCommandImpl;
import com.vcs.util.Result;

/**
 * created by wuzh on 2017/5/21 19:45
 */
public class Help extends BaseCommandImpl {

    @Override
    public Result execute(String input) {
        if(input.equals("help")){
            StringBuilder sb = new StringBuilder();
            sb.append("指令集如下:").append("\n");
            sb.append("  add [file] : 追踪文件(夹),将文件(夹)添加的暂存区").append("\n");
            sb.append("  cd [path] : 进入path路径").append("\n");
            sb.append("  cd .. : 回到父路径").append("\n");
            sb.append("  cd - : 回到上一次的路径").append("\n");
            sb.append("  checkout [feature] : 切换到feature分支").append("\n");
            sb.append("  commit -m '[message]' : 提交暂存区的内容").append("\n");
            sb.append("  diff [file1] [file2] : 对比两个文件的区别 ").append("\n");
            sb.append("  edit [file] : 打开文件编辑").append("\n");
            sb.append("  init : 在当前目录下创建一个仓库").append("\n");
            sb.append("  log : 查看当前分支的commit记录").append("\n");
            sb.append("  ls : 查看当前目下的文件列表").append("\n");
            sb.append("  merge [feature] : 合并当前分支和指定的分支").append("\n");
            sb.append("  mkdir [file] : 在当目录下创建指定的文件夹").append("\n");
            sb.append("  newBranch [feature] : 在版本库创建新的分支").append("\n");
            sb.append("  reflog : 查看当前分支的版本号变化记录 ").append("\n");
            sb.append("  reset file -b 0 : 将文件从暂存区删除").append("\n");
            sb.append("  status [-t] 查看暂存区文件列表 ; status [-v] 查看版本的文件列表: ").append("\n");
            sb.append("  touch [file] : 在当前路径创建指定的文件").append("\n");
            sb.append(" ").append("\n");
            result.setOutput(sb.toString());
            return result;
        }
        result.setMessage("help命令不带参数");
        return result;
    }
}

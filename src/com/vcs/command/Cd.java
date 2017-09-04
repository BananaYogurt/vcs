package com.vcs.command;

import com.vcs.util.CurrentBranch;
import com.vcs.util.Env;
import com.vcs.util.DirectoryTool;
import com.vcs.util.Result;
import org.dom4j.DocumentException;

import java.io.File;
import java.io.IOException;

/**
 * created by 吴震煌 on 2017/3/7 14:34
 */
public class Cd {

    private String param;

    private String errorMsg = "没有找到指定的路径";

    private String lastPath = "~";

    private File file;

    private File f1;

    private File f2;

    private Result result = new Result();

    private String parentPath;

    private DirectoryTool dt = new DirectoryTool();

    private String flag;

    private String fileName;

    public Result execute(String input)throws IOException, DocumentException{
        //将命令行用空格隔开
        String[] s = input.split(" ");

        //命令只包含cd,依然是当前路径
        if(s.length == 1){
            result.setError(false);
            return result;
        }
        //命名格式或者参数错误
        else if(s.length > 2){
            result.setMessage(errorMsg);
            return result;
        }else{
            //获得参数或者路径
            param = s[1];
            if("-".equals(param)){
                updateCurrentPath(lastPath);
                return result;
            }
            else if("..".equals(param)){
                //返回到上一层的工作路径
                file = new File(Env.path);
                try{
                    parentPath = file.getParentFile().getPath();
                    lastPath = Env.path;
                    updateCurrentPath(parentPath);
                }catch (Exception e){
                    //parentPath = Env.path;
                }
                return result;
            } else {
                //-----------------------华丽的分割线 ------------------
                //进入子路径
                System.out.println("param == " + param);

                flag = Env.path; //flag表示当前的文件路径

                //初始目录下，不属于系统的任何目录
                if(flag == "~"){
                    fileName = formatPath(param);
                    //比如  abc 直接提示没有该路径
                    file = new File(fileName);
                    if(!file.isDirectory())
                        result.setMessage(errorMsg);
                    else
                        updateCurrentPath(fileName);
                    return result;
                }
                //如果已经在某个目录下面了，比如flag = d:\ param = annotation  d:\annotation
                fileName = formatPath(flag + "\\" + param);
                file = new File(fileName);
                //新的file如果不是文件夹类型，则说明说明在当前目录下没有该目录 Evn.path + param的文件夹不存在
                if(!file.isDirectory()){
                    fileName = formatPath(param);
                    //则有可能是直接切换到另一个文件夹的命令 flag = c:\ param = d:\
                    file = new File(fileName);
                    if(file.isDirectory()){
                        updateCurrentPath(formatPath(fileName));
                        return result;
                    }else{
                        result.setMessage(errorMsg);
                        return result;
                    }
                }
                updateCurrentPath(fileName);
                return result;
            }
            //-------------------华丽的分割线 ------------------------------
        }

    }

    /**
     * 用来更新系统的当前路径,并且判断当前是master还是branch或者没有在任何枝干下面
     * 并且实时更新控制台的前缀
     * @param param 路径
     */
    private void updateCurrentPath(String param) throws IOException, DocumentException {
        lastPath = Env.path;
        Env.path = param;
        Env.path = Env.path.toLowerCase();
        if(dt.isDepository(Env.path)){
            //File vcsConfig = dt.getCvsConfig();
            Env.branch = "("+ CurrentBranch.getCurrentBranch()+")";
        }else{
            Env.branch = "";
        }
        Env.prefix = "\n"+Env.USER_NAME+"@"+Env.COMPUTER_NAME+" "+Env.path+" "+Env.branch+"\n ";
        result.setError(false);
    }


    /**
     * 用来规避异常
     * @param fileName 合计的初始路径
     * @return 合法的文件路径  比如 E:\annotation\abc\ --> E:\annotation\abc ; d: --> d:\
     */
    private String formatPath(String fileName){

        //去掉字符串结尾的若干斜杠  abc\\\\\ --> acb
        String src = formatString(fileName);

        f1 = new File(fileName);

        //这种情况比较特殊 比如 new File("abc)的parent == null或者 new File("d:")也是
        //实际上文件的路径在工作目录下
        if(null == f1.getParent()){

            f2 = new File(fileName);
            if(f2.getAbsolutePath().equals(fileName))
                return fileName;  // 比如c:\
            fileName = fileName + "\\";
            f2 = new File(fileName);
            if(f2.getAbsolutePath().equals(fileName))
                return fileName;
            return src;
        }
        return f1.getAbsolutePath();

    }

    /**
     * 递归去掉文件末尾的斜杠
     * @param s 字符串
     * @return 去掉斜杠的字符串
     */
    private String formatString(String s){
        if(s.charAt(s.length() -1) != '\\'){
            return s;
        }
        return formatString(s.substring(0,s.length()-1));
    }
}

package com.vcs.command;

import com.vcs.command.inf.BaseCommandImpl;
import com.vcs.components.ContentMap;
import com.vcs.components.Version;
import com.vcs.util.*;
import org.dom4j.DocumentException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

/**
 * created by wuzh on 2017/5/22 6:10
 */
public class Reset extends BaseCommandImpl {

    @Override
    public Result execute(String input) {


        String[] s = input.split(" ");

        try {
            if(!DirectoryTool.isDepository(Env.path)){
                result.setMessage(" 当前不在仓库路径!无法执行回退操作");
                return result;
            }
        }catch (IOException e){
            result.setMessage(e.getMessage());
            return result;
        }


        if(s.length == 2){
            int index  = -1;
            index = s[1].indexOf("head-");
            if(s[1].indexOf("head-") == 0 ){
                String headNumber = s[1].substring(5,s[1].length());
                try{
                    //回退的几格
                    int number = Integer.parseInt(headNumber);
                    //获得当前版本
                    Version version = FindVersion.getVersionByPath(CurrentVersion.getVersionPath());
                    for(; number >0 ; number --){
                        if(!"".equals(version.getPre())){
                            version = FindVersion.getVersionById(version.getPre());
                        }else{
                            break;
                        }
                    }

                    //修改.version <head></head>里面的值  reflog的值也要修改
                    VersionTool.setVersionHead(version.getVersionPath(),version.getVersionId());
                    //BranchTool
                    BranchTool.checkout(CurrentBranch.getCurrentBranch(),false);

                }catch (Exception e){
                    e.printStackTrace();
                    result.setMessage(e.getMessage());
                    return result;
                }
            }
        }

        if(s.length == 3){
            //切换到该版本
            if(s[1].equals("-v")){
                String versionId  = s[2].toUpperCase();
                try {
                    if(null!= FindVersion.getVersionById(versionId)){
                        Version version2 = FindVersion.getVersionById(versionId);
                        VersionTool.setVersionHead(version2.getVersionPath(),versionId);
                        BranchTool.checkout(CurrentBranch.getCurrentBranch(),false);
                        result.setOutput("版本号"+versionId+"的文件已经成功导出");
                        return result;
                    }else{
                        result.setMessage("版本号"+versionId+"在当前分支下不存在！可以通过log查看版本ID");
                        return result;
                    }
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //reset file -b -1
        if(s.length == 4 && s[2].equals("-b")){
            String versionPath = null;
            String cachePath = null;
            int back = 0;
            try {
                 back = Integer.parseInt(s[3].substring(1,s[3].length()));
            }catch (Exception e){
                e.printStackTrace();
                result.setMessage("reset file -b -n 命令n位自然数");
                return result;
            }

            try {
                versionPath = CurrentVersion.getVersionPath();
                Version version3 = FindVersion.getVersionByPath(versionPath);
                cachePath = version3.getCachePath();
                //ContentMap cache = FindContent.findContentByPath(cachePath);

            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if( 0 > back){
                return result;
            }
            //删除该文件

            if( 0 == back){
                String fileName = Env.path + "\\"+s[1];
                int i = PropertiesTool.delProperty(new File(cachePath),fileName,"");
                if(i == 0){
                    result.setOutput("文件"+s[1]+"已经成功从暂存区删除\n");
                    return result;
                }else{
                    result.setOutput("暂存区不存在文件"+s[1]+"\n");
                    return result;
                }
            }
        }

        result.setError(false);
        return result;
    }
}

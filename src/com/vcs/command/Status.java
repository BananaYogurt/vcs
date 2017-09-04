package com.vcs.command;

import com.vcs.command.inf.BaseCommandImpl;
import com.vcs.components.ContentMap;
import com.vcs.components.Version;
import com.vcs.util.*;
import org.dom4j.DocumentException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

/**
 * created by wuzh on 2017/5/28 5:52
 */
public class Status extends BaseCommandImpl {

    @Override
    public Result execute(String input) {
        try {
            if(!DirectoryTool.isDepository(Env.path)){
                result.setMessage("当前不在仓库路径!无法查看查看版本库状态");
                return result;
            }
        }catch (IOException e){
            result.setMessage(e.getMessage());
            return result;
        }

        String[] s = input.split(" ");

        StringBuilder resultSb = new StringBuilder();
        String versionPath ;
        Version version ;
        String cachePath = "";
        String contentPath = "";
        ContentMap contentOrCache = null;

        if(s.length == 2){

            try {
                versionPath = CurrentVersion.getVersionPath();
                version = FindVersion.getVersionByPath(versionPath);
                cachePath = version.getCachePath();
                contentPath = version.getPrePath();
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(s[1].equals("-t")){
                resultSb.append("当前暂存区文件列表如下:").append("\n");
                try {
                    contentOrCache = FindContent.findContentByPath(cachePath);
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(s[1].equals("-v")){
                resultSb.append("当前提交区文件列表如下:").append("\n");
                try {
                    contentOrCache = FindContent.findContentByPath(contentPath);
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            Map<String,String> map  = contentOrCache.getFilesMap();
            if(map.size() == 0){
                resultSb.append("抱歉,什么都没有找到^_^,文件列表为空\n");
            }
            for(String key : map.keySet()){
                resultSb.append(key).append("\n");
            }

            result.setOutput(resultSb.toString());
            return result;

        }else{
            result.setMessage("status的命令参数不正确，详情使用help查看具体使用");
            return result;
        }

    }
}

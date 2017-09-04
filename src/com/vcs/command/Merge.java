package com.vcs.command;

import com.vcs.command.inf.BaseCommandImpl;
import com.vcs.components.Branch;
import com.vcs.components.ContentMap;
import com.vcs.components.Version;
import com.vcs.util.*;
import org.dom4j.DocumentException;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Map;


/**
 * created by wuzh on 2017/5/4 21:58
 */
public class Merge extends BaseCommandImpl{

    @Override
    public Result execute(String input) {

        param = input.split(" ");

        VcsConfigParameter vcsConfig = new VcsConfigParameter(Env.vcsConfig);

        try {
            if(!DirectoryTool.isDepository(Env.path)){
                result.setMessage("当前不在仓库路径!无法合并分支");
                return result;
            }
        }catch (IOException e){
            result.setMessage(e.getMessage());
            return result;
        }

        if(param.length == 1){
            result.setMessage("缺少要合并的分支");
            return result;
        }

        if(param.length == 2){
            try {

                String feature = CurrentBranch.getCurrentBranch();

                String name = param[1];

                Branch b = FindBranch.findBranchByName(name);

                if(null == b){
                    result.setMessage(" 本地没有<"+param[1]+">分支，无法执行合并操作");
                    return result;
                }

                if(b.getName().equals(feature)){

                    result.setMessage(" 当前分支为<"+feature+">,无需再合并自身");
                    return result;
                }

                String versionPath = CurrentVersion.getVersionPath();

                Version version = FindVersion.getVersionByPath(versionPath);

                String contentPath = version.getContentPath() ;

                File content = new File(contentPath);
                //把cache的内容添加进来
                String cachePath = version.getCachePath();
                //没commit 之前 content的内容是空的,所有要想把之前preContent以及cache 复制过来
                File cache = new File(cachePath);

                if(version.getPre().equals("")){
                    PropertiesTool.merge(content,cache,"");
                }else{
                    FileCopyTool.fileCopy(new File(version.getPrePath()),content);
                    PropertiesTool.merge(content,cache,"");
                }

                ContentMap contentMap1 = FindContent.findContentByPath(contentPath);

                Map<String,String> filesMap = contentMap1.getFilesMap();

                Version v = FindVersion.getVersionByPath(b.getVersionPath());

                //String contentPath2 = v.getContentPath();

                ContentMap contentMap2;

                if(v.getPre().equals("")){
                    result.setMessage("{"+b.getName()+"}分支还没有提交记录,没有内容可以合并");
                    return result;
                }else{
                    String prePath = v.getPrePath();
                    contentMap2 = FindContent.findContentByPath(prePath);
                }

                Map<String,String> filesMap2 = contentMap2.getFilesMap();

                CompressTool compressTool = new CompressTool();

                StringBuilder resultSB = new StringBuilder();

                for(Map.Entry<String,String> entry : filesMap.entrySet()){
                    if(filesMap2.containsKey(entry.getKey())){
                        //两个版本的文件的文件名相同但是数字签名不同 说明文件名相同 内容不一样
                        if(!entry.getValue().equals(filesMap2.get(entry.getKey()))){
                            //获得文件的绝对路径 如 absolute = d\:\\cuf\\repo\\hello.txt
                            String absolute = entry.getKey();
                            //压缩后的文件完整路径 d\:\\cuf\\repo\\.vcs\\depository\\cbf628a7ed8e522c5b6650a1c22c738f9bde1353@28
                            String sourceName = entry.getValue();
                            //要解压的文件
                            File compressSource = new File(sourceName);
                            //另一个解压的文件名 用来合并的
                            String targetName = filesMap2.get(absolute);
                            //需要解压对比的文件
                            File compressTarget = new File(targetName);
                            //解压后的文件
                            File source = compressTool.decompress(compressSource,absolute);
                            //解压后要对比的文件 文件名一样，用.tar区别 对比后删除
                            File target = compressTool.decompress(compressTarget,absolute+".target");
                            //合并的类
                            com.vcs.algorithm.Merge merge = new com.vcs.algorithm.Merge();
                            //执行合并的函数
                            merge.execute(source,target);
                            //合并之后的文件的文件名称
                            String compressName = "";
                            //判断合并是否产生冲突
                            if(merge.isSuccess()){
                                //success == true 表明合并成功
                                try {
                                    compressName = compressTool.compress(source,vcsConfig.getDepository());
                                } catch (NoSuchAlgorithmException e) {
                                    e.printStackTrace();
                                }
                                //  "d\:\\cuf\\repo\\.vcs\\depository\\" + "compressName"
                                filesMap.put(entry.getKey(),compressSource.getParent()+"\\"+compressName);
                                resultSB.append("'").append(absolute).append("'== 合并成功\n");

                            }else{
                                //合并失败
                                resultSB.append("'").append(absolute).append("'>>合并失败,冲突需要手动解决并重新add和commit\n");
                            }
                            //将.tar的文件删除
                            target.delete();
                        }
                        //如果文件完全相同
                        filesMap2.remove(entry.getKey());
                    }
                }
                //当前分支的名称
                String main = CurrentBranch.getCurrentBranch();
                for(Map.Entry<String,String> entry : filesMap2.entrySet()){
                    resultSB.append("\t'"+entry.getKey()+"' 从{"+name+"}分支添加到{"+main+"}\n");
                }
                //将两个分支的合并版本的文件列表放到Map里面
                filesMap.putAll(filesMap2);
                //更新当前分支.version文件   合并相当于先提交再合并 所有要更新两次 这是第一次更新version
                UpdateVersion.updateVersion(versionPath,version.getVersionId(),"merge feature{"+ name +"} to feature{"+ main +"}",b);
                //更新后的那一行数据
                Version newVersion  = FindVersion.getVersionByPath(versionPath);
                //更新的一行的contentPath的路径
                String newContentPath = newVersion.getContentPath();
                //新的content文件的内容
                File newContent = new File(newContentPath);
                //将合并的map的数据写入new content中
                PropertiesTool.MapToFile(filesMap,newContent,"merge feature{"+ name +"} to feature{"+ main +"}");
                //然后再提交一次，.version再加一行
                UpdateVersion.updateVersion(versionPath,newVersion.getVersionId(),null,null);

                result.setOutput(resultSB.toString());

                System.out.println("【com.vcs.command.Merge】log : 合并{"+feature+"}和{"+b.getName()+"}分支");

                return result;

            } catch (IOException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //格式不正确的情况下
        result.setMessage(" merge命令格式不正确");
        return result;

    }

}

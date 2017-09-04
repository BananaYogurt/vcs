package com.vcs.command;

import com.vcs.command.inf.BaseCommandImpl;
import com.vcs.util.*;
import org.dom4j.DocumentException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * created by wuzh on 2017/4/29 19:44
 */
public class Add extends BaseCommandImpl {

    private List<File> fileList = new ArrayList<>();

    //压缩工具
    private CompressTool compresser = new CompressTool();

    private void getAllFiles(File root)throws IOException{

        if(root.isDirectory()){
            File[] files = root.listFiles();
            for(File f :files){
                getAllFiles(f);
            }
        }else if(root.isFile()){
            fileList.add(root);
        }

    }

    /**
     * 可以追踪单个文件，也可以追踪整个文件夹
     * @param input
     * @return
     */
    @Override
    public Result execute(String input) {

        try {
            if(!DirectoryTool.isDepository(Env.path)){
                result.setMessage("当前不在仓库路径!无法跟踪文件");
                return result;
            }
        }catch (IOException e){
            result.setMessage(e.getMessage());
            return result;
        }

        param = input.split(" ");

        if(param.length == 2){
            String fileName = param[1];
            File target = new File (Env.path+"\\"+fileName);
            if(!target.exists()){
                result.setMessage("跟踪的目标文件不存在");
                return result;
            }else{
                //将单个文件放入暂存区
                if(target.isFile()){
                    //当前文件
                    try {

                        add(target);

                        result.setOutput("文件 \""+target.getName()+"\" 已经成功添加到暂存区^_^");

                        return result;

                    }  catch (NoSuchAlgorithmException ne) {
                        ne.printStackTrace();
                        result.setMessage(ne.getMessage());
                        return result;
                    } catch (IOException ie) {
                        ie.printStackTrace();
                        result.setMessage(ie.getMessage());
                        return result;

                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                }
                if(target.isDirectory()){
                    try {
                        //想清空列表
                        fileList.clear();
                        getAllFiles(target);
                        for(File f : fileList ){
                            add(f);
                        }
                        result.setOutput("文件夹 \""+target.getName()+"\" 已经成功添加到暂存区^_^");
                    }catch (IOException e){
                        result.setMessage(e.getMessage());
                        return result;
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else{
            result.setMessage("命令格式错误，详情help查询用法");
            return result;
        }
        return result;

    }

    private void add(File target) throws IOException, NoSuchAlgorithmException, DocumentException {

        VcsConfigParameter vcsConfig = new VcsConfigParameter(Env.vcsConfig);

        //压缩 D:\cuf\repo\.vcs\depository + 文件的数字签名 + "=" + 文件字节长度
        String fileName = compresser.compress(target,vcsConfig.getDepository());
        //获得暂存区配置的名称 如 AQ7YMOJWBP
        String currentCache = CurrentCache.getCurrentCache();
        //完整的暂存区配置文件的路径 D:\cuf\repo\.vcs\object\versions\cache\AQ7YMOJWBP
        String cachePath = vcsConfig.getCache() + currentCache;
        //文件输入流
        File cacheFile = new File(cachePath);
        PropertiesTool.addProperty(cacheFile,target.getAbsolutePath(),fileName,"");

        System.out.println("【"+this.getClass().getName()+"】@log : 已成功将文件 "+target.getName()+" 添加到暂存区");

    }
}

package com.vcs.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * created by 吴震煌 on 2017/3/14 14:56
 */
public class DirectoryTool {
    /**
     * 路径下面如果有个.vcs的文件夹，并且.vcs里面有个vcsConfig文件,
     * 说明当前文件是一个版本仓库
     * @param path 参数路径
     * @return 判断当前路径是否是版本仓库
     */
    public static boolean isDepository(String path)throws IOException{

        File file = new File(path);
        File[] listFiles = file.listFiles();
        if(null != listFiles){
            for(File f : listFiles){
                if(f.isDirectory() && ".vcs".equals(f.getName())){
                    File[] files = f.listFiles();
                    if(null != files){
                        for(File f2 : files){
                            if(f2.isFile() && "vcsConfig".equals(f2.getName())){

                                Env.vcsConfig = f2;

                                FileInputStream fis = new FileInputStream(Env.vcsConfig);

                                Properties prop = new Properties();

                                prop.load(fis);

                                Env.branches = new File(prop.getProperty("branches"));

                                return true;
                            }
                        }
                    }
                }
            }
        }

        String parent = file.getParent();
        if(null != parent){
            return isDepository(parent);
        }
        //如果不是，当前的配置文件为空
        Env.vcsConfig = null;
        Env.branches = null;
        return false;
    }

}

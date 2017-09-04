package com.vcs.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * created by wuzh on 2017/5/1 15:11
 */
public class VcsConfigParameter {

    private String rootPath;

    private String branches;

    private String content;

    private String cache;

    private String depository;

    private String logs;

    public VcsConfigParameter(File vcsConfig){
        Properties prop  = new Properties();

        try{

            FileInputStream fis = new FileInputStream(vcsConfig);

            prop.load(fis);

            rootPath = prop.getProperty("rootPath");

            branches = prop.getProperty("branches");

            content = prop.getProperty("content");

            cache = prop.getProperty("cache");

            depository = prop.getProperty("depository");

            logs = prop.getProperty("logs");

        }catch (IOException ie){
            ie.printStackTrace();
        }

    }

    public String getRootPath() {
        return rootPath;
    }

    public String getBranches() {
        return branches;
    }

    public String getContent() {
        return content;
    }

    public String getCache() {
        return cache;
    }

    public String getDepository() {
        return depository;
    }

    public String getLogs() {
        return logs;
    }
}

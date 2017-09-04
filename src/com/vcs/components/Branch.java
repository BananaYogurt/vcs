package com.vcs.components;

import java.util.ArrayList;
import java.util.List;

/**
 * created by wuzh on 2017/4/29 18:37
 */
public class Branch {

    private String name ;

    private boolean notNull;

    private String versionPath;

    public Branch(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public String getVersionPath() {
        return versionPath;
    }

    public void setVersionPath(String versionPath) {
        this.versionPath = versionPath;
    }

    @Override
    public String toString() {
        return "log : <branch name=\""+this.name+"\" not-null=\""+this.notNull+"\" versionPath=\""+this.versionPath+"\">";
    }
}

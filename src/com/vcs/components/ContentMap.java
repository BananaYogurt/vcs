package com.vcs.components;

import java.util.HashMap;
import java.util.Map;

/**
 * created by wuzh on 2017/4/29 18:39
 */
public class ContentMap {

    private Map<String,String> filesMap = new HashMap<>();

    public Map<String, String> getFilesMap() {
        return filesMap;
    }

    public void setFilesMap(Map<String, String> filesMap) {
        this.filesMap = filesMap;
    }
}

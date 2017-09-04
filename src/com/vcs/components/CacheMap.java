package com.vcs.components;

import java.util.HashMap;
import java.util.Map;

/**
 * created by wuzh on 2017/5/28 6:09
 */
public class CacheMap {

    private Map<String,String> filesMap = new HashMap<>();

    public Map<String, String> getFilesMap() {
        return filesMap;
    }

    public void setFilesMap(Map<String, String> filesMap) {
        this.filesMap = filesMap;
    }
}

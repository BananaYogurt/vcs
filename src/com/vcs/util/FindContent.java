package com.vcs.util;

import com.vcs.components.ContentMap;
import org.dom4j.DocumentException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * created by wuzh on 2017/5/7 18:02
 */
public class FindContent {

    public static ContentMap findContentByPath(String contentPath) throws DocumentException, IOException {

        Map<String,String> map = new HashMap<>();

        Properties prop = new Properties();

        File content = new File(contentPath);

        FileInputStream fis = new FileInputStream(content);

        prop.load(fis);

        ContentMap contentMap = new ContentMap();

        Iterator<String> it = prop.stringPropertyNames().iterator();

        while (it.hasNext()){

            String key = it.next();

            map.put(key,prop.getProperty(key));
        }

        fis.close();

        contentMap.setFilesMap(map);

        return contentMap;

    }
}

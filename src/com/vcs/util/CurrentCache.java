package com.vcs.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;

/**
 * created by wuzh on 2017/5/4 14:11
 */
public class CurrentCache {

    public static String getCurrentCache() throws DocumentException {

        String versionPath  = CurrentVersion.getVersionPath();

        //String versionPath  = "d:\\cuf\\repo\\.vcs\\object\\versions\\hello.version";

        if( null == versionPath){
            return null;
        }

        File versionFile = new File(versionPath);

        SAXReader saxReader = new SAXReader();

        Document document = saxReader.read(versionFile);

        Element versions = document.getRootElement();

        Element temp = versions.element("temp");

        return temp.getText();

    }

}

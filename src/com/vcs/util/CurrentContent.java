package com.vcs.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;

/**
 * created by wuzh on 2017/5/4 20:06
 */
public class CurrentContent {

    public static String getCurrentContent() throws DocumentException {

        String versionPath  = CurrentVersion.getVersionPath();

        if( null == versionPath){
            return null;
        }

        File versionFile = new File(versionPath);

        SAXReader saxReader = new SAXReader();

        Document document = saxReader.read(versionFile);

        Element versions = document.getRootElement();

        Element head = versions.element("head");

        return head.getText();

    }
}

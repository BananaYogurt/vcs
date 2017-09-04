package com.vcs.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;

/**
 * created by wuzh on 2017/4/30 10:56
 */
public class CurrentBranch {


    //用来获得当前的分支
    public static String getCurrentBranch() throws IOException, DocumentException {

        SAXReader saxReader = new SAXReader();

        Document document = saxReader.read(Env.branches);

        Element root = document.getRootElement();

        Element main  = root.element("main");

        return main.getText();
    }


}

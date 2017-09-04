package com.vcs.util;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.List;

/**
 * created by wuzh on 2017/5/4 14:13
 */
public class CurrentVersion {


    public static String getVersionPath() throws DocumentException {

        SAXReader saxReader = new SAXReader();

        Document document = saxReader.read(Env.branches);

        //Document document = saxReader.read(new File("D:\\cuf\\repo\\.vcs\\object\\branch"));

        Element root = document.getRootElement();

        Element main  = root.element("main");

        List<Element> list = root.elements("branch");


        for(int i = 0 ; i < list.size() ; i ++){

            Element e =  list.get(i);

            List<Attribute> la = e.attributes();

            for(int j = 0 ; j < la.size(); j ++){

                Attribute attr = la.get(j);

                if(attr.getName().equals("name") && attr.getValue().equals(main.getText())){

                    //<branch name="master" not-null="true" versionPath="d:\cuf\repo\.vcs\object\versions\master.version"/>
                    attr = la.get(j+2);

                    //System.out.println("当前分支的版本的暂存区路径是 = "+attr.getValue());

                    return attr.getValue();
                }
            }

        }
        return null;
    }

}

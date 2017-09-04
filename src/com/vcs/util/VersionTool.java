package com.vcs.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * created by wuzh on 2017/5/25 20:00
 */
public class VersionTool {

    public static void setVersionHead(String versionPath,String versionId) throws DocumentException, IOException {

        File versionFile = new File(versionPath);

        SAXReader saxReader = new SAXReader();

        Document document = saxReader.read(versionFile);

        Element versions = document.getRootElement();

        Element head = versions.element("head");

        head.setText(versionId);

       /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = new Date();

        ReflogTool.updateReflog(versionId+"@reset:"+CurrentBranch.getCurrentBranch()+"@"+sdf.format(date)+"\n");*/

        FileOutputStream fos = new FileOutputStream(versionFile);

        OutputFormat format = new OutputFormat("",true);

        XMLWriter xmlWriter = new XMLWriter(fos,format);

        xmlWriter.write(document);

        xmlWriter.close();

        fos.close();

    }
}

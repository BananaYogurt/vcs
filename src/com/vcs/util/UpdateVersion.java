package com.vcs.util;

import com.vcs.components.Branch;
import com.vcs.components.Version;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * created by wuzh on 2017/5/8 21:59
 */
public class UpdateVersion {

    public static void updateVersion(String versionPath, String contentName ,String message, Branch branch) throws IOException, DocumentException, ParseException {

        VcsConfigParameter vcsConfig = new VcsConfigParameter(Env.vcsConfig);

        File versionFile = new File(versionPath);

        String versionId = RandomFactory.getSerialString();

        String cacheId = RandomFactory.getSerialString();

        SAXReader saxReader = new SAXReader();

        Document document = saxReader.read(versionFile);

        Element versions = document.getRootElement();

        List<Element> elements = versions.elements("version");

        List<Attribute> attributes ;

        for(Element e : elements){

            attributes = e.attributes();

            if( attributes.get(1).getValue().equals(contentName)){

                e.attribute("commit").setValue("true");

                String next = e.attribute("next").getValue();

                if(next.equals("")){
                    e.attribute("next").setValue(versionId);
                }else{
                    e.attribute("next").setValue(next + "+" + versionId);
                }

                if(null != message){
                    e.addAttribute("message",message);
                }

                break;
            }
        }

        Element version = versions.addElement("version");

        Element head = versions.element("head");

        head.setText(versionId);

        Element temp = versions.element("temp");

        temp.setText(cacheId);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        version.addAttribute("time",sdf.format(new Date()));

        version.addAttribute("versionId",versionId);

        version.addAttribute("cacheId",cacheId);

        version.addAttribute("commit","false");

        version.addAttribute("pre",contentName);

        version.addAttribute("next","");

        if(null != branch){

            Element parent = version.addElement("parent");

            String vPath = branch.getVersionPath();

            Version v = FindVersion.getVersionByPath(vPath);

            parent.addAttribute("vId",branch.getName()+"."+v.getPre());

            parent.addAttribute("vPath",v.getVersionPath());
        }

        FileOutputStream fos = new FileOutputStream(versionFile);

        OutputFormat format = new OutputFormat("",true);

        XMLWriter xmlWriter = new XMLWriter(fos,format);

        xmlWriter.write(document);

        fos.close();

        xmlWriter.close();

        String newCachePath = vcsConfig.getCache() + cacheId;

        String newContentPath = vcsConfig.getContent() + versionId;

        new File(newCachePath).createNewFile();

        new File(newContentPath).createNewFile();
    }
}

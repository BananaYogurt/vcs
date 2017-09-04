package com.vcs.util;

import com.vcs.components.Version;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * created by wuzh on 2017/5/7 17:29
 */
public class FindVersion {

    /**
     * 获得版本头
     * @param versionPath
     * @return
     * @throws DocumentException
     * @throws ParseException
     */
    //versionPath="e:\temp\.vcs\object\versions\master.version
    public static Version getVersionByPath(String versionPath) throws DocumentException, ParseException {

        int i = versionPath.lastIndexOf("\\");

        String parent = versionPath.substring(0,i+1) + "content\\";

        String contentPath = new VcsConfigParameter(Env.vcsConfig).getContent();

        String cachePath = new VcsConfigParameter(Env.vcsConfig).getCache();

        File target = new File(versionPath);

        SAXReader saxReader = new SAXReader();

        Document document = saxReader.read(target);

        Element versions = document.getRootElement();

        String head = versions.element("head").getText();

        List<Element> list = versions.elements("version");

        Version version = new Version();

        for(Element e : list){

            if(e.attribute("versionId").getValue().equals(head)){

                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                version.setVersionPath(versionPath);

                version.setTime(format.parse(e.attribute("time").getValue()));

                version.setVersionId(head);

                version.setCacheId(e.attribute("cacheId").getValue());

                version.setCommit(Boolean.parseBoolean(e.attribute("commit").getValue()));

                version.setPre(e.attribute("pre").getValue());

                if(e.attribute("pre").getValue().equals("")){
                    version.setPrePath("");
                }else{
                    version.setPrePath(parent+e.attribute("pre").getValue());
                }

                version.setNext(e.attribute("next").getValue());

                try{
                    if(null != e.attribute("message")){
                        version.setMessage(e.attribute("message").getValue());
                    }
                }catch (Exception e1){e1.printStackTrace();}

                version.setContentPath(contentPath+head);

                //后加的
                version.setCachePath(cachePath + e.attribute("cacheId").getValue());

                return version;
            }
        }

        return null;
    }

    public static Version getVersionById(String versionId) throws DocumentException, ParseException {

        String versionPath = CurrentVersion.getVersionPath();

        String contentPath = new VcsConfigParameter(Env.vcsConfig).getContent();

        String cachePath = new VcsConfigParameter(Env.vcsConfig).getCache();

        File target = new File(versionPath);

        SAXReader saxReader = new SAXReader();

        Document document = saxReader.read(target);

        Element versions = document.getRootElement();

        List<Element> list = versions.elements("version");

        Version version = new Version();

        for(Element e : list){

            if(e.attribute("versionId").getValue().equals(versionId)){

                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                version.setVersionPath(versionPath);

                version.setTime(format.parse(e.attribute("time").getValue()));

                version.setVersionId(versionId);

                version.setCacheId(e.attribute("cacheId").getValue());

                version.setCommit(Boolean.parseBoolean(e.attribute("commit").getValue()));

                version.setPre(e.attribute("pre").getValue());

                if(e.attribute("pre").getValue().equals("")){
                    version.setPrePath("");
                }else{
                    version.setPrePath(contentPath+e.attribute("pre").getValue());
                }

                version.setNext(e.attribute("next").getValue());

                try{
                    if(null != e.attribute("message")){
                        version.setMessage(e.attribute("message").getValue());
                    }
                }catch (Exception e1){e1.printStackTrace();}

                version.setContentPath(contentPath+versionId);

                //后加的
                version.setCachePath(cachePath + e.attribute("cacheId").getValue());

                return version;
            }
        }

        return null;
    }
}

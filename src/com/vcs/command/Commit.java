package com.vcs.command;

import com.vcs.components.Version;
import com.vcs.util.*;
import com.vcs.command.inf.BaseCommandImpl;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * created by 吴震煌 on 2017/3/9 15:21
 */
public class Commit extends BaseCommandImpl{

    //1.将暂存区的配置内容copy到保存区的
    //2.在version里面更新 新head和新temp
    @Override
    public Result execute(String input){

        try {
            if(!DirectoryTool.isDepository(Env.path)){
                result.setMessage("当前不在仓库路径!无法提交分支");
                return result;
            }
        }catch (IOException e){
            result.setMessage(e.getMessage());
            return result;
        }

        param = input.split(" ");

        if(param.length == 1 || (param.length == 2 && param[1].equals("-m"))){
            result.setMessage("commit 命令缺少,需要提交信息,commit -m \"message\"");
            return result;
        }
        if(param.length == 2){
            result.setMessage("commit 命令格式错误！");
            return result;
        }
        String s1 = param[param.length -1];

        boolean condition1 = param[1].equals("-m");

        boolean condition2 = (param[2].charAt(0) =='"'&& s1.charAt(s1.length() -1) == '"' );

        boolean condition3 = (param[2].charAt(0) =='\''&& s1.charAt(s1.length() -1) == '\'');

        if( condition1 && ( condition2|| condition3)){


            StringBuilder sb = new StringBuilder();

            //这个循环是用来还原input split的空格，使得commit的message正常
            for(int i = 2 ;i < param.length;i++){
                sb.append(param[i]).append(" ");
            }

            String message = sb.substring(1,sb.length() -2);

            System.out.println("log : commit的信息 = "+ message);

            //1.
            VcsConfigParameter vcsConfig = new VcsConfigParameter(Env.vcsConfig);

            String contentPath = "" ;

            String cachePath = "";

            String lastContent = "";

            try {
                //提交区的路径
                contentPath = vcsConfig.getContent() + CurrentContent.getCurrentContent();
                //暂存区的路径
                cachePath = vcsConfig.getCache() + CurrentCache.getCurrentCache();
                //获得未更新前的版本id
                lastContent = CurrentContent.getCurrentContent();

            } catch (DocumentException e) {
                e.printStackTrace();
            }

            File cache = new File(cachePath);

            File content = new File(contentPath);


            Version currentVersion ;

            try {
                 currentVersion = FindVersion.getVersionByPath(CurrentVersion.getVersionPath());

                 if(currentVersion.getPre().equals("")){

                     //FileCopyTool.fileCopy(cache,content);
                     PropertiesTool.merge(content,cache,"first commit,commit message ='"+message+"'");

                 } else{
                     String preContentPath = vcsConfig.getContent() + currentVersion.getPre();

                     File preContent = new File(preContentPath);
                     //先将之前的提交区的内容添加到到当前的提交区
                     FileCopyTool.fileCopy(preContent,content);
                     //再将暂存区的内容太添加到当前的提交区
                     PropertiesTool.merge(content,cache,"commit message = '"+message+"';commit from v("+preContent.getName()+") to v("+content.getName()+")");

                 }
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(cache.length() == 0){
                result.setMessage("暂存区为空,不能执行commit操作!");
                return result;
            }

            //2.更新version的head和temp
            String versionId = RandomFactory.getSerialString();

            String cacheId = RandomFactory.getSerialString();

            String versionPath = null;

            try {
                versionPath = CurrentVersion.getVersionPath();
            } catch (DocumentException e) {
                    e.printStackTrace();
            }

            File versionFile = null;
            if(null != versionPath){
                versionFile = new File(versionPath);
            }

            SAXReader saxReader = new SAXReader();

            Document document = null;

            try {
                if( null != versionFile){
                    document = saxReader.read(versionFile);
                }

            } catch (DocumentException e) {
                e.printStackTrace();
            }

            //添加commit message
            //
            Element versions = document.getRootElement();

            List<Element> list = versions.elements("version");

            //Element oldVersion = null;

            List<Attribute> list2 = null;

            for(Element e : list){

                list2 = e.attributes();

                if(list2.get(1).getValue().equals(lastContent)){

                    System.out.println("log : add message success! ");

                    e.attribute("commit").setValue("true");

                    String oldNext = e.attribute("next").getValue();

                    if(oldNext.equals("")){

                        e.attribute("next").setValue(versionId);
                    }else{
                        // 该版本的派生版本
                        e.attribute("next").setValue(oldNext+"+"+versionId);
                    }

                    e.addAttribute("message",message);

                    break;
                }

            }
            //oldVersion.addAttribute("message",message);

            Element version = versions.addElement("version");

            Element head = versions.element("head");

            head.setText(versionId);

            Element temp = versions.element("temp");

            temp.setText(cacheId);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date time = new Date();

            version.addAttribute("time",sdf.format(time));

            version.addAttribute("versionId",versionId);

            version.addAttribute("cacheId",cacheId);

            version.addAttribute("commit","false");

            version.addAttribute("pre",lastContent);

            version.addAttribute("next","");

            FileOutputStream fos = null;
            try {
                if(null != versionFile){
                    fos = new FileOutputStream(versionFile);
                }
            } catch (FileNotFoundException e) {
                   e.printStackTrace();
            }

            OutputFormat format = new OutputFormat("",true);

            XMLWriter xmlWriter = null;
            try {
                xmlWriter = new XMLWriter(fos,format);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String newCachePath = vcsConfig.getCache() + cacheId;

            String newContentPath = vcsConfig.getContent() + versionId;

            try {
                xmlWriter.write(document);

                fos.close();

                xmlWriter.close();

                new File(newCachePath).createNewFile();

                new File(newContentPath).createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {

                LogTool.updateLog(lastContent+"@commit:"+message+"@"+sdf.format(time)+"\n");

                ReflogTool.updateReflog(versionId+"@commit:"+message+"@"+sdf.format(time)+"\n");

            } catch (IOException e) {
                e.printStackTrace();
            }

            result.setOutput("commit 完成^_^ \t {version id} = "+lastContent);
            return result;

        }
        result.setMessage("commit 命令格式错误！");
        return result;
    }


}

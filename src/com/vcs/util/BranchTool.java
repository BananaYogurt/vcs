package com.vcs.util;

import com.vcs.components.*;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * created by wuzh on 2017/4/30 16:15
 */
public class BranchTool {

    private static SAXReader saxReader = new SAXReader();

    private static VcsConfigParameter parameter;

    private static Document document;

    private static Element root;

    /*public static final boolean do_checkout = true;

    public static final boolean do_reset = false;*/

    public static List<String> loadBranchName() throws DocumentException {

        List<String> list = new ArrayList<>();
        //当前如果没用进入仓库
        if(Env.branches != null){

            document = saxReader.read(Env.branches);

            root = document.getRootElement();

            Iterator<?> it = root.elementIterator();

            while(it.hasNext()){
                Element element = (Element) it.next();

                System.out.println("<"+element.getName()+">");

                if(element.getName().equals("branch")){

                    Attribute attr = element.attribute("name");

                    list.add(attr.getValue());
                }

            }
            return list;
        }
        return null;
    }

    public static boolean addBranch(String value){

        parameter = new VcsConfigParameter(Env.vcsConfig);

        try{

            //******************branch.version的初始化过程

            //版本号的id
            String versionId = RandomFactory.getSerialString();

            //暂存区的id
            String cacheId = RandomFactory.getSerialString();

            String rootPath = parameter.getRootPath();

            String versionPath = rootPath+"\\.vcs\\object\\versions\\"+value+".version";

            String content = parameter.getContent()+"\\"+versionId;

            String cache = parameter.getCache()+"\\"+cacheId;

            //当添加一个分支的时候，就为该分支创建一个版本文件列表
            new File(content).createNewFile();
            new File(cache).createNewFile();

            //创建该分支对应的版本列表branch.version
            File branchVersion = new File(versionPath);

            //
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Element versions = DocumentHelper.createElement("versions");

            Document doc = DocumentHelper.createDocument(versions);

            OutputFormat format = new OutputFormat("",true);

            Element head = versions.addElement("head");

            head.setText(versionId);

            Element temp = versions.addElement("temp");

            temp.setText(cacheId);

            Element version = versions.addElement("version");

            version.addAttribute("time",sdf.format(new Date()));

            version.addAttribute("versionId",versionId);

            version.addAttribute("cacheId",cacheId);

            version.addAttribute("commit","false");

            version.addAttribute("pre","");

            version.addAttribute("next","");

            FileOutputStream fs = new FileOutputStream(branchVersion);

            XMLWriter writer = new XMLWriter(fs,format);

            writer.write(doc);

            fs.close();

            writer.close();

            //*********************** branch配置初始化的过程
            document = saxReader.read(Env.branches);

            root = document.getRootElement();

            Element main = root.element("main");

            Element branch = root.addElement("branch");

            branch.addAttribute("name",value);

            branch.addAttribute("not-null","false");

            branch.addAttribute("versionPath",versionPath);

            main.setText(value);

            FileOutputStream fos = new FileOutputStream(Env.branches);

            XMLWriter xmlWriter = new XMLWriter(fos,format);

            xmlWriter.write(document);

            fos.close();

            xmlWriter.close();

            //***********************************


        }catch (Exception e){

            e.printStackTrace();

            return false;
        }

        //更新当前branch名称
        Env.branch = "("+value+")";
        //更新当前显示前缀
        Env.prefix = "\n"+Env.USER_NAME+"@"+Env.COMPUTER_NAME+" "+Env.path+" "+Env.branch+"\n ";

        return true;
    }


    public static void checkout(String branch,boolean flag) throws IOException, DocumentException, ParseException {

        document = saxReader.read(Env.branches);

        root = document.getRootElement();

        Element main = root.element("main");

        main.setText(branch);

        Writer writer = new OutputStreamWriter(new FileOutputStream(Env.branches));

        OutputFormat format = new OutputFormat("",true);

        XMLWriter xmlWriter = new XMLWriter(writer,format);

        xmlWriter.write(document);

        xmlWriter.close();

        writer.close();

        /* 以上代码是更新 branch文件里面的 <main>branch</main> */

        //接下来是将版本content里面的全部内容还原出来 从压缩文件解压
        //1）先获得content的路径
        com.vcs.components.Branch b = FindBranch.findBranchByName(branch);

        Version v = FindVersion.getVersionByPath(b.getVersionPath());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = new Date();

        if(flag)
            ReflogTool.updateReflog(v.getVersionId()+"@checkout:"+branch+"@"+sdf.format(date)+"\n");
        else
            ReflogTool.updateReflog(v.getVersionId()+"@reset:"+branch+"@"+sdf.format(date)+"\n");

        String prePath;

        if(!v.getPre().equals("")){

            prePath = v.getPrePath();

        }else{
            prePath = v.getCachePath();
        }

        ContentMap content = FindContent.findContentByPath(prePath);

        CompressTool compressTool = new CompressTool();

        Map<String,String> map = content.getFilesMap();

        for(Map.Entry<String,String> entry : map.entrySet() ){

            compressTool.decompress(new File(entry.getValue()),entry.getKey());

            System.out.println("【com.vcs.util.BranchTool】log :{"+branch+"}分支的 '"+entry.getKey()+"' 成功导出");
        }



    }

}

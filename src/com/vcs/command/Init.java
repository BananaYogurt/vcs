package com.vcs.command;

import com.vcs.util.*;
import com.vcs.command.inf.BaseCommandImpl;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * created by 吴震煌 on 2017/3/14 9:20
 */
public class Init extends BaseCommandImpl {

    private static final String ERROR_MSG = " init命令格式不正确!";

    private static final String ERROR_CONFLICT = " 该目录已经是仓库或包含在仓库内,不需要重复创建!";

    private static final String ERROR_FORBID = " ~ 还没有进入路径,不允许初始化仓库！";

    private String branch;

    private String masterVersion;

    private String content;

    private String vcsDepository;

    private String log;

    //暂存
    private String cache;

    private File reflog;

    @Override
    public Result execute(String input){

        param = input.split(" ");

        //命令为init
        if(param.length == 1)
        {
            try {
                //当前路径不是仓库的话或者不包含在仓库下面
                if(!DirectoryTool.isDepository(Env.path)) {
                    if(Env.path.equals("~")){
                        result.setMessage(ERROR_FORBID);
                        return result;
                    }
                    String vcs = Env.path + "\\.vcs\\";
                    String vcsConfig = vcs+"vcsConfig";
                    String vcsObject = vcs+"object\\";
                    vcsDepository = vcs+"depository\\";
                    branch = vcsObject+"branch";
                    masterVersion = vcsObject+"versions\\master.version";
                    content = vcsObject+"versions\\content\\";
                    cache = vcsObject+"versions\\cache\\";
                    log = vcsObject+"versions\\logs\\";
                    try {
                        //.vcs位仓库的主目录
                        File root = new File(vcs);
                        root.mkdir();
                        //.vcs设置为隐藏目录
                        Runtime.getRuntime().exec("attrib +H "+ root.getAbsolutePath());
                        //vcsConfig位仓库的配置
                        new File(vcsConfig).createNewFile();
                        //为vcsConfig初始配置
                        initConfig(new File(vcsConfig));
                        //object用来保存每个版本的压缩文件
                        new File(vcsObject).mkdir();
                        //depository用来存放文件
                        new File(vcsDepository).mkdir();
                        //所有主分支和分支的配置文件
                        createBranch(new File(branch));
                        //创建主分支master的version信息
                        new File(vcsObject+"versions\\").mkdir();
                        //版本文件列表明细文件夹
                        new File(content).mkdir();
                        //暂存去文件夹
                        new File(cache).mkdir();
                        //logs mkdir
                        new File(log).mkdir();
                        //
                        new File(log+"master.log").createNewFile();

                        reflog = new File(log + "master.reflog");
                        reflog.createNewFile();

                        initVersion();
                        Env.branch = "(master)";
                        Env.prefix = "\n"+Env.USER_NAME+"@"+Env.COMPUTER_NAME+" "+Env.path+" "+Env.branch+"\n ";
                        result.setOutput("已经在"+ vcs +"目录下完成版本仓库的初始化");
                    } catch (IOException e) {
                        e.printStackTrace();
                        result.setMessage(e.toString());
                    }
                }
                else {
                    result.setMessage(ERROR_CONFLICT);
                }
            }catch (IOException ie){
                ie.printStackTrace();
            }
        }
        else {
            result.setMessage(ERROR_MSG);
        }
        return result;
    }


    //创建版本仓库时候初始化master分支
    public void createBranch(File file) throws IOException {

        Element root = DocumentHelper.createElement("branches");

        Document document = DocumentHelper.createDocument(root);

        Element main = root.addElement("main");

        main.addText("master");

        Element branch = root.addElement("branch");

        branch.addAttribute("name","master");

        branch.addAttribute("not-null","true");

        branch.addAttribute("versionPath",masterVersion);

        FileOutputStream fos = new FileOutputStream(file);

        OutputFormat format = new OutputFormat("",true);

        XMLWriter xmlWriter = new XMLWriter(fos,format);

        xmlWriter.write(document);

        fos.close();

        xmlWriter.close();

    }

    //创建版本
    public void initVersion()throws IOException{

        File file = new File(masterVersion);

        Element versions = DocumentHelper.createElement("versions");

        Document doc = DocumentHelper.createDocument(versions);

        Element head = versions.addElement("head");

        Element temp = versions.addElement("temp");

        //随机生成的版本号 10位
        String versionId = RandomFactory.getSerialString();

        //当创建master分支后为master的版本创建一个版本文件列表
        new File(content+"\\"+versionId).createNewFile();
        //随机生成暂存区版的文件 id
        String cacheId = RandomFactory.getSerialString();
        //生成暂存区文件
        new File(cache+"\\"+cacheId).createNewFile();

        head.setText(versionId);

        temp.setText(cacheId);

        Element version = versions.addElement("version");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = new Date();

        ReflogTool.updateReflog(reflog,versionId + "@initialize:master@"+sdf.format(date)+"\n");

        version.addAttribute("time",sdf.format(date));

        version.addAttribute("versionId",versionId);

        version.addAttribute("cacheId",cacheId);

        version.addAttribute("commit","false");

        //version.addAttribute("message","");

        version.addAttribute("pre","");

        version.addAttribute("next","");

        FileOutputStream fos = new FileOutputStream(file);

        OutputFormat format = new OutputFormat("",true);

        XMLWriter xmlWriter = new XMLWriter(fos,format);

        xmlWriter.write(doc);

        fos.close();

        xmlWriter.close();

        //file.createNewFile();

    }


    //vcsConfig文件的初始化,本质上是properties文件
    public void initConfig(File file) throws IOException{

        FileOutputStream fos = new FileOutputStream(file);

        Properties properties = new Properties();

        properties.setProperty("vid",String.valueOf(System.currentTimeMillis()));

        properties.setProperty("rootPath",Env.path);

        properties.setProperty("branches",branch);

        properties.setProperty("content",content);

        properties.setProperty("cache",cache);

        properties.setProperty("depository",vcsDepository);

        properties.setProperty("logs",log);

        properties.store(fos,"vcs配置列表");

        fos.close();
    }


}

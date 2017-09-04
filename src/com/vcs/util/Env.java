package com.vcs.util;

import java.io.File;

/**
 * created by 吴震煌 on 2017/3/7 10:15
 */
public class Env {

    public static final String USER_NAME = "【"+System.getProperty("user.name")+"】";

    public static final String COMPUTER_NAME = System.getenv().get("COMPUTERNAME");

    public static final String APP_NAME = "《版本控制器》";

    public static final String VERSION = "V 1.0.1";

    public static final String RIGHT = "作者:吴震煌 仅供参考和娱乐 输入help获得详细信息";

    public static String path = "~";

    public static String branch = "";

    public static String prefix = "\n"+USER_NAME+"@"+COMPUTER_NAME+" "+path+" "+branch+"\n ";

    public static File vcsConfig ;

    public static File branches;
}

package com.vcs.util;

import com.vcs.command.*;

/**
 * created by 吴震煌 on 2017/3/13 16:20
 */
public class CMDContent {

    public Result result = new Result();

    public Cd cd = new Cd();

    public Ls ls = new Ls();

    public Mkdir mkdir = new Mkdir();

    public Touch touch = new Touch();

    public Init init = new Init();

    public NewBranch newBranch = new NewBranch();

    public Checkout checkout= new Checkout();

    public Add add= new Add();

    public Commit commit = new Commit();

    public Diff diff = new Diff();

    public Merge merge = new Merge();

    public Log log = new Log();

    public Reflog reflog = new Reflog();

    public Edit edit = new Edit();

    public Help help = new Help();

    public Reset reset = new Reset();

    public Remote remote = new Remote();

    public InitService initService = new InitService();

    public Status status = new Status();

    public Delete delete = new Delete();

}

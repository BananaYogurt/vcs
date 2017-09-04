package com.vcs.command.inf;

import com.vcs.util.Result;

/**
 * created by 吴震煌 on 2017/3/13 16:00
 */
public class BaseCommandImpl implements BaseCommand {

    public Result result = new Result();

    public String[] param;

    @Override
    public Result execute(String input) {
        return null;
    }
}

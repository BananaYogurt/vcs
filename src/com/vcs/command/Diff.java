package com.vcs.command;

import com.vcs.algorithm.DiffMatchPatch;
import com.vcs.command.inf.BaseCommandImpl;
import com.vcs.util.Env;
import com.vcs.util.FileToString;
import com.vcs.util.Result;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

/**
 * created by wuzh on 2017/4/4 21:55
 */
public class Diff extends BaseCommandImpl {

    @Override
    public Result execute(String input) {

        // diff f1 f2 || diff -c f1 f2 (character to character)
        param = input.split(" ");


        if(param.length == 3){

        }
        if(param.length == 4 && param[1].equals("-c")){
            String fname1 = Env.path +"\\"+ param[2];
            String fname2 = Env.path +"\\"+ param[3];
            File f1 = new File(fname1);
            File f2 = new File(fname2);
            if(!f1.exists() || !f2.exists()){
                result.setMessage("对比的文件可能不存在,建议用ls命令查看确认");
                return result;
            }
            if( !f1.isFile() || !f2.isFile()){
                result.setMessage("对比的两者都不能文件夹");
                return result;
            }
            DiffMatchPatch dmp = new DiffMatchPatch();
            try {
                LinkedList<DiffMatchPatch.Diff> diffs =  dmp.diff_main(FileToString.toString(f1),FileToString.toString(f2),true);
                LinkedList<DiffMatchPatch.Patch> patches = dmp.patch_make(diffs);
                String str = (dmp.patch_toText(patches));
                result.setOutput(str);
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
            result.setError(false);
            return result;
        }
        result.setError(false);
        return result;
    }

    private String format(String s){
        return s.replace("%BB",">>").replace("%0A","¿");
    }
}

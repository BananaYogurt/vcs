package com.vcs.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * created by 吴震煌 on 2017/3/7 11:40
 */
public class Command extends CMDContent{



    private String input;

    private String notFoundMsg;

    private String instruction;



    public String execute(String input) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException {

        this.input = input.replaceAll("\\n+", "").trim();

        System.out.println("input = "+this.input);

        instruction = this.input.split(" ")[0];

        notFoundMsg  = "\n没有找到'"+input+"'相关的指令,请输入help获取帮助";

        for(Instruction instr : Instruction.values()){
            if(instruction.equals(instr.toString())){
                return getResult(this.input,instruction);
            }
        }
        return notFoundMsg;

    }

    private String getResult(String input,String instruction) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class clazz = this.getClass();
        Field field = clazz.getField(instruction);
        Object obj = field.get(this);
        clazz = obj.getClass();
        Method execute = clazz.getMethod("execute",String.class);
        result = (Result) execute.invoke(obj,input);
        if(result.isError){
            return result.getMessage();
        }else{
            return result.getOutPut();
        }
    }

}

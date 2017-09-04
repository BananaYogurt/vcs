package com.vcs.util;

/**
 * created by 吴震煌 on 2017/3/8 9:12
 */
public class Result {

    public boolean isError;

    public String message;

    public String output;

    public Result(){}

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
        if(!isError) {
            this.message = "";
        }
    }

    public void setOutput(String output){
        this.isError = false;
        this.message = "";
        this.output = "\n"+output;
    }

    public String getOutPut(){
        return this.output;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.isError = true;
        this.message = message;
        this.output = "";
    }
}

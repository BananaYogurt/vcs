package com.vcs.components;

/**
 * created by wuzh on 2017/5/7 18:37
 */
public class FileInfo {

    private String fileFullName ;

    private String signature;

    private String length;

    public String getFileFullName() {
        return fileFullName;
    }

    public void setFileFullName(String fileFullName) {
        this.fileFullName = fileFullName;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
}

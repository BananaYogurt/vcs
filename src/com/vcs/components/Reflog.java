package com.vcs.components;

/**
 * created by wuzh on 2017/5/10 13:17
 */
public class Reflog {

    private String versionId;

    private String message;

    private String time;

    public Reflog(String versionId, String message, String time) {
        this.versionId = versionId;
        this.message = message;
        this.time = time;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}

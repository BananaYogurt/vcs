package com.vcs.components;

import java.util.Date;

/**
 * created by wuzh on 2017/4/29 18:49
 */
public class Version {

    private String versionId;

    private String cacheId;

    private Date time;

    private boolean commit;

    private String pre;

    private String next;

    private String message;

    private String contentPath;

    private String versionPath;

    private String cachePath;

    private String prePath ;

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getCacheId() {
        return cacheId;
    }

    public void setCacheId(String cacheId) {
        this.cacheId = cacheId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isCommit() {
        return commit;
    }

    public void setCommit(boolean commit) {
        this.commit = commit;
    }

    public String getPre() {
        return pre;
    }

    public void setPre(String pre) {
        this.pre = pre;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public String getVersionPath() {
        return versionPath;
    }

    public void setVersionPath(String versionPath) {
        this.versionPath = versionPath;
    }

    public String getPrePath() {
        return prePath;
    }

    public void setPrePath(String prePath) {
        this.prePath = prePath;
    }

    public String getCachePath() {
        return cachePath;
    }

    public void setCachePath(String cachePath) {
        this.cachePath = cachePath;
    }

    @Override
    public String toString() {
        return "Version{" +
                "versionId='" + versionId + '\'' +
                ", cacheId='" + cacheId + '\'' +
                ", time=" + time +
                ", commit=" + commit +
                ", pre='" + pre + '\'' +
                ", next='" + next + '\'' +
                ", message='" + message + '\'' +
                ", contentPath='" + contentPath + '\'' +
                ", versionPath='" + versionPath + '\'' +
                ", cachePath='" + cachePath + '\'' +
                ", prePath='" + prePath + '\'' +
                '}';
    }
}

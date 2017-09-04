package com.vcs.algorithm;

/**
 * created by wuzh on 2017/4/27 20:00
 */
public class LineHashCode {

    private int lineNumber;

    private Operation operation;

    private String text;

    private Integer hashCode;

    public LineHashCode(String text){
        this.text = text;
        this.hashCode = text.hashCode();
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getHashCode() {
        return hashCode;
    }

    public void setHashCode(Integer hashCode) {
        this.hashCode = hashCode;
    }

    public void deleteText(){
        this.text = "@------" + this.text;
    }

    public void insertText(){
        this.text = "@++++++" + this.text;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}

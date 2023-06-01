package com.lzb.onlinejava.complier.dto;

public class SubjectResult {
    private int status;
    private String info;
    private String input;
    private String output;
    private String expect;


    public SubjectResult() {
    }

    public SubjectResult(int status, String info) {
        this.status = status;
        this.info = info;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }
}

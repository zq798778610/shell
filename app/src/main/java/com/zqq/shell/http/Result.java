package com.zqq.shell.http;

public class Result<T> {
    private String str;
    private boolean success;
    private T result;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Result{" +
                "str='" + str + '\'' +
                ", success=" + success +
                ", result=" + result +
                '}';
    }
}

package com.example.groovemax1.uitest.net;

/**
 * 文件名：
 * 描述：任务单元
 * 作者：
 * 时间：
 */
public abstract class ThreadPoolTask implements Runnable {

    protected String url;

    public ThreadPoolTask(String url) {
        this.url = url;
    }

    public abstract void run();

    public String getURL() {
        return this.url;
    }
}

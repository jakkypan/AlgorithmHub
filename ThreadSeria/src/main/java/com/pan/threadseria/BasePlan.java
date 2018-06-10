package com.pan.threadseria;

/**
 * Created by panda on 2018/6/8
 **/
public abstract class BasePlan {
    // 当前是否在打印字符
    protected boolean isChar = false;
    protected IThreadPrintCallback printCallback;
    protected int numStart;
    // 是否需要停止数字答应的循环
    protected boolean isStopNumeral = false;

    public BasePlan(IThreadPrintCallback printCallback) {
        this.printCallback = printCallback;
        numStart = 1;
    }

    public void reset() {
        isChar = false;
        numStart = 1;
        isStopNumeral = false;
    }

    /**
     * 数字打印的线程
     *
     * @return
     */
    public abstract Runnable numeralThread();

    /**
     * 字符打印的线程
     *
     * @return
     */
    public abstract Runnable characterThread(final String[] charArrays);
}

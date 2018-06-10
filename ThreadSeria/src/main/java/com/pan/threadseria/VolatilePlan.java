package com.pan.threadseria;

import android.util.Log;

/**
 * volatile本身没有阻塞的效果，主要通过被volatile修饰的变量来做阻塞逻辑
 *
 * Created by panda on 2018/6/8
 **/
public class VolatilePlan extends BasePlan{

    private volatile boolean isChar = false;

    public VolatilePlan(IThreadPrintCallback printCallback) {
        super(printCallback);
    }

    public Runnable numeralThread() {
        return new Runnable() {
            public void run() {
                try {
                    while (!isStopNumeral) {
                        while (isChar) {
                            // empty block
                        }

                        if (printCallback != null) {
                            printCallback.print(numStart++);
                        }
                        Thread.sleep(100);
                        isChar = true;
                    }
                } catch (InterruptedException e) {
                    Log.e("111", "numeralThread error...", e);
                }
            }
        };
    }

    public Runnable characterThread(final String[] charArrays) {
        return new Runnable() {
            public void run() {
                try {
                    for (int i = 0; i < charArrays.length; i++) {
                        while (!isChar) {
                            // empty block
                        }

                        if (printCallback != null) {
                            printCallback.print(charArrays[i]);
                        }
                        if (i > charArrays.length - 1) {
                            isStopNumeral = true;
                        }
                        Thread.sleep(100);
                        isChar = false;
                    }
                } catch (InterruptedException e) {
                    Log.e("111", "characterThread error...", e);
                }
            }
        };
    }
}

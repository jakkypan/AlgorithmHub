package com.pan.threadseria;

import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * atomic的实现其实是volatile的扩展，AtomicXXXX的内部变量是通过volatile保证原子性的
 *
 * Created by panda on 2018/6/8
 **/
public class AtomicPlan extends BasePlan {
    private AtomicBoolean isChar = new AtomicBoolean(false);

    public AtomicPlan(IThreadPrintCallback printCallback) {
        super(printCallback);
    }

    public Runnable numeralThread() {
        return new Runnable() {
            public void run() {
                try {
                    while (!isStopNumeral) {
                        while (isChar.get()) {
                            // empty block
                        }

                        if (printCallback != null) {
                            printCallback.print(numStart++);
                        }
                        Thread.sleep(100);
                        isChar.set(true);
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
                        while (!isChar.get()) {
                            // empty block
                        }

                        if (printCallback != null) {
                            printCallback.print(charArrays[i]);
                        }
                        if (i > charArrays.length - 1) {
                            isStopNumeral = true;
                        }
                        Thread.sleep(100);
                        isChar.set(false);
                    }
                } catch (InterruptedException e) {
                    Log.e("111", "characterThread error...", e);
                }
            }
        };
    }
}

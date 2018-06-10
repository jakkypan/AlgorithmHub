package com.pan.threadseria;

import android.util.Log;

/**
 * 采用synchronized和wait/notify的方案
 *
 * Created by panda on 2018/6/8
 **/
public class NotifyWaitPlan extends BasePlan {
    private Object o = new Object();

    public NotifyWaitPlan(IThreadPrintCallback printCallback) {
        super(printCallback);
    }

    public Runnable numeralThread() {
        return new Runnable() {
            public void run() {
                try {
                    while (!isStopNumeral) {
                        synchronized (o) {
                            if (isChar) {
                                o.wait();
                            }

                            if (printCallback != null) {
                                printCallback.print(numStart++);
                            }
                            Thread.sleep(100);
                            isChar = true;
                            o.notify();
                        }
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
                        synchronized (o) {
                            if (!isChar) {
                                o.wait();
                            }

                            if (printCallback != null) {
                                printCallback.print(charArrays[i]);
                            }
                            if (i >= charArrays.length - 1) {
                                isStopNumeral = true;
                                break;
                            }
                            Thread.sleep(100);
                            isChar = false;
                            o.notify();
                        }
                    }
                } catch (InterruptedException e) {
                    Log.e("111", "characterThread error...", e);
                }
            }
        };
    }
}

package com.pan.threadseria;

import android.util.Log;

import java.util.concurrent.Semaphore;

/**
 * Created by panda on 2018/6/10
 **/
public class SemaphorePlan extends BasePlan {
    // 只有1个信号量
    private Semaphore semaphore = new Semaphore(1);

    public SemaphorePlan(IThreadPrintCallback printCallback) {
        super(printCallback);
    }

    public Runnable numeralThread() {
        return new Runnable() {
            public void run() {
                try {
                    while (!isStopNumeral) {
                        if (!isChar) {
                            semaphore.acquire();
                            if (printCallback != null) {
                                printCallback.print(numStart++);
                            }
                            Thread.sleep(100);
                            semaphore.release();
                            isChar = true;
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
                    for (int i = 0; i < charArrays.length;) {
                        if (isChar) {
                            semaphore.acquire();

                            if (printCallback != null) {
                                printCallback.print(charArrays[i]);
                            }
                            if (i >= charArrays.length - 1) {
                                isStopNumeral = true;
                            }
                            Thread.sleep(100);
                            semaphore.release();
                            isChar = false;
                            i++;
                        }

                    }
                } catch (InterruptedException e) {
                    Log.e("111", "characterThread error...", e);
                }
            }
        };
    }
}

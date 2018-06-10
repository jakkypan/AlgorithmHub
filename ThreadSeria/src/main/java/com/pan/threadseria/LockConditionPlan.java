package com.pan.threadseria;

import android.util.Log;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 采用lock和condition的方案
 *
 * Created by panda on 2018/6/8
 **/
public class LockConditionPlan extends BasePlan {
    private Lock lock = new ReentrantLock(true);
    private Condition condition = lock.newCondition();

    public LockConditionPlan(IThreadPrintCallback printCallback) {
        super(printCallback);
    }

    public Runnable numeralThread() {
        return new Runnable() {
            public void run() {
                try {
                    while (!isStopNumeral) {
                        lock.lock();
                        if (isChar) {
                            condition.await();
                        }

                        if (printCallback != null) {
                            printCallback.print(numStart++);
                        }
                        Thread.sleep(100);
                        isChar = true;
                        condition.signal();
                    }
                } catch (InterruptedException e) {
                    Log.e("111", "numeralThread error...", e);
                } finally {
                    lock.unlock();
                }
            }
        };
    }

    public Runnable characterThread(final String[] charArrays) {
        return new Runnable() {
            public void run() {
                try {
                    for (int i = 0; i < charArrays.length; i++) {
                        lock.lock();
                        if (!isChar) {
                            condition.await();
                        }

                        if (printCallback != null) {
                            printCallback.print(charArrays[i]);
                        }
                        if (i >= charArrays.length - 1) {
                            isStopNumeral = true;
                        }
                        Thread.sleep(100);
                        isChar = false;
                        condition.signal();
                    }
                } catch (InterruptedException e) {
                    Log.e("111", "characterThread error...", e);
                } finally {
                    lock.unlock();
                }
            }
        };
    }
}

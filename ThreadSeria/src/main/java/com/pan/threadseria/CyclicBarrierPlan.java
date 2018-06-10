package com.pan.threadseria;

import android.util.Log;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by panda on 2018/6/8
 **/
public class CyclicBarrierPlan extends BasePlan {
    private final CyclicBarrier barrier;
    private final List<String> list;

    public CyclicBarrierPlan(IThreadPrintCallback printCallback) {
        super(printCallback);
        list = Collections.synchronizedList(new LinkedList<String>());
        barrier = new CyclicBarrier(2, printBarrierAction());
    }

    /**
     * barrierAction
     *
     * @return
     */
    private Runnable printBarrierAction() {
        return new Runnable() {
            @Override
            public void run() {
                Collections.sort(list);
                for(String s : list) {
                    if (printCallback != null) {
                        printCallback.print(s);
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                list.clear();
            }
        };
    }

    public Runnable numeralThread() {
        return new Runnable() {
            public void run() {
                try {
                    while (!isStopNumeral) {
                        list.add((numStart++) + "");
                        barrier.await(100, TimeUnit.MILLISECONDS);
                    }
                } catch (InterruptedException e) {
                    Log.e("111", "numeralThread error...", e);
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public Runnable characterThread(final String[] charArrays) {
        return new Runnable() {
            public void run() {
                try {
                    for (int i = 0; i < charArrays.length; i++) {
                        if (i > charArrays.length - 1) {
                            isStopNumeral = true;
                        }
                        list.add(charArrays[i]);
                        barrier.await(100, TimeUnit.MILLISECONDS);
                    }
                } catch (InterruptedException e) {
                    Log.e("111", "characterThread error...", e);
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}

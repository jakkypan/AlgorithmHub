package com.pan.threadseria;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by panda on 2018/6/8
 **/
public class BlockingQueuePlan extends BasePlan {
    /**
     * 阻塞队列
     */
    private final ArrayBlockingQueue<Boolean> queue1 = new ArrayBlockingQueue<>(1);
    private final ArrayBlockingQueue<Boolean> queue2 = new ArrayBlockingQueue<>(1);

    public BlockingQueuePlan(IThreadPrintCallback printCallback) {
        super(printCallback);
    }

    public Runnable numeralThread() {
        return new Runnable() {
            public void run() {
                while (!isStopNumeral) {
                    if (printCallback != null) {
                        printCallback.print(numStart++);
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        queue2.put(true);
                        queue1.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    public Runnable characterThread(final String[] charArrays) {
        return new Runnable() {
            public void run() {
                for (int i = 0; i < charArrays.length; i++) {
                    try {
                        queue2.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (printCallback != null) {
                        printCallback.print(charArrays[i]);
                    }
                    if (i >= charArrays.length - 1) {
                        isStopNumeral = true;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        queue1.put(true);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
}

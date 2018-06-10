package com.pan.threadseria;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by panda on 2018/6/10
 **/
public class HandlerPlan extends BasePlan {
    private Handler handler1;
    private Handler handler2;
    private int arrIndex;

    public HandlerPlan(IThreadPrintCallback printCallback) {
        super(printCallback);
        arrIndex = 0;
    }

    @Override
    public void reset() {
        super.reset();
        arrIndex = 0;
    }

    @Override
    public Runnable numeralThread() {
        return new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                handler1 = new Handler(Looper.myLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        if (printCallback != null) {
                            printCallback.print(numStart++);
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler2.sendEmptyMessage(0);
                    }
                };
                Looper.loop();
            }
        };
    }

    @Override
    public Runnable characterThread(final String[] charArrays) {
        return new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                handler2 = new Handler(Looper.myLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        if (printCallback != null) {
                            printCallback.print(charArrays[arrIndex++]);
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (arrIndex <= charArrays.length - 1) {
                            handler1.sendEmptyMessage(0);
                        }
                    }
                };
                Looper.loop();
            }
        };
    }

    public void doPrint() {
        // 等待handle完成实例化
        while (handler1 == null && handler2 == null) { }
        handler1.sendEmptyMessage(0);
    }
}

package com.pan.threadseria;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by panda on 2018/6/10
 **/
public class HandlerPlan2 {
    private Handler handler1;
    private Handler handler2;

    private IThreadPrintCallback printCallback;
    private int numStart;
    private String[] charArrays;
    private int arrIndex;

    public HandlerPlan2(IThreadPrintCallback printCallback, String[] charArrays) {
        this.printCallback = printCallback;
        numStart = 1;
        this.charArrays = charArrays;
        arrIndex = 0;
    }

    public void reset() {
        numStart = 1;
        arrIndex = 0;
    }

    public void doPrint() {
        new Thread(new Runnable() {
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
        }).start();

        new Thread(new Runnable() {
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
        }).start();

        // 等待handle完成实例化
        while (handler1 == null && handler2 == null) { }
        handler1.sendEmptyMessage(0);
    }
}

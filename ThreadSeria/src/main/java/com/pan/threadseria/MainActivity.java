package com.pan.threadseria;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by panda on 2018/6/8
 **/
public class MainActivity extends AppCompatActivity implements IThreadPrintCallback {

    private ExecutorService executorService;
    private String[] charArrays;

    TextView tv;

    private NotifyWaitPlan notifyWaitPlan;
    private LockConditionPlan lockConditionPlan;
    private VolatilePlan volatilePlan;
    private AtomicPlan atomicPlan;
    private CyclicBarrierPlan cyclicBarrierPlan;
    private BlockingQueuePlan blockingQueuePlan;
    private SemaphorePlan semaphorePlan;
    private HandlerPlan2 handlerPlan2;
    private HandlerPlan handlerPlan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        charArrays = buildCharArr(26);
        tv = findViewById(R.id.content);

        notifyWaitPlan = new NotifyWaitPlan(this);
        lockConditionPlan = new LockConditionPlan(this);
        volatilePlan = new VolatilePlan(this);
        atomicPlan = new AtomicPlan(this);
        cyclicBarrierPlan = new CyclicBarrierPlan(this);
        blockingQueuePlan = new BlockingQueuePlan(this);
        semaphorePlan = new SemaphorePlan(this);
        handlerPlan2 = new HandlerPlan2(this, charArrays);
        handlerPlan = new HandlerPlan(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.one:
                tv.setText("");
                notifyWaitPlan.reset();
                if (executorService != null) {
                    executorService.shutdownNow();
                }
                executorService = Executors.newFixedThreadPool(2);
                executorService.submit(notifyWaitPlan.numeralThread());
                executorService.submit(notifyWaitPlan.characterThread(charArrays));
                break;
            case R.id.two:
                tv.setText("");
                lockConditionPlan.reset();
                if (executorService != null) {
                    executorService.shutdownNow();
                }
                executorService = Executors.newFixedThreadPool(2);
                executorService.submit(lockConditionPlan.numeralThread());
                executorService.submit(lockConditionPlan.characterThread(charArrays));
                break;
            case R.id.three:
                tv.setText("");
                volatilePlan.reset();
                if (executorService != null) {
                    executorService.shutdownNow();
                }
                executorService = Executors.newFixedThreadPool(2);
                executorService.submit(volatilePlan.numeralThread());
                executorService.submit(volatilePlan.characterThread(charArrays));
                break;
            case R.id.four:
                tv.setText("");
                atomicPlan.reset();
                if (executorService != null) {
                    executorService.shutdownNow();
                }
                executorService = Executors.newFixedThreadPool(2);
                executorService.submit(atomicPlan.numeralThread());
                executorService.submit(atomicPlan.characterThread(charArrays));
                break;
            case R.id.five:
                tv.setText("");
                cyclicBarrierPlan.reset();
                if (executorService != null) {
                    executorService.shutdownNow();
                }
                executorService = Executors.newFixedThreadPool(2);
                executorService.submit(cyclicBarrierPlan.numeralThread());
                executorService.submit(cyclicBarrierPlan.characterThread(charArrays));
                break;
            case R.id.six:
                tv.setText("");
                blockingQueuePlan.reset();
                if (executorService != null) {
                    executorService.shutdownNow();
                }
                executorService = Executors.newFixedThreadPool(2);
                executorService.submit(blockingQueuePlan.numeralThread());
                executorService.submit(blockingQueuePlan.characterThread(charArrays));
                break;
            case R.id.seven:
                tv.setText("");
                semaphorePlan.reset();
                if (executorService != null) {
                    executorService.shutdownNow();
                }
                executorService = Executors.newFixedThreadPool(2);
                executorService.submit(semaphorePlan.numeralThread());
                executorService.submit(semaphorePlan.characterThread(charArrays));
                break;
            case R.id.eight:
                tv.setText("");
                handlerPlan2.reset();
                handlerPlan2.doPrint();
                break;
            case R.id.eight2:
                tv.setText("");
                handlerPlan.reset();
                if (executorService != null) {
                    executorService.shutdownNow();
                }
                executorService = Executors.newFixedThreadPool(2);
                executorService.submit(handlerPlan.numeralThread());
                executorService.submit(handlerPlan.characterThread(charArrays));
                handlerPlan.doPrint();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void print(final Object value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv.setText(tv.getText().toString() + value + "  ");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdownNow();
    }

    /**
     * 构建26个字母
     *
     * @param max
     * @return
     */
    public String[] buildCharArr(int max) {
        String[] charArr = new String[max];
        int tmp = 65;
        for(int i=0;i<max;i++){
            charArr[i] = String.valueOf((char)(tmp+i));
        }
        return charArr;
    }
}

# CountDownLatch

它的作用是task A必须等到其他N个task都执行完毕之后才能执行的场景

主要函数：

```java
//调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
public void await() throws InterruptedException { };   
//和await()类似，只不过等待一定的时间后count值还没变为0的话就会继续执行
public boolean await(long timeout, TimeUnit unit) throws InterruptedException { };  
//将count值减1
public void countDown() { };  
```

例子：

```java
public static void main(String[] args) {   
         final CountDownLatch latch = new CountDownLatch(2);
          
         new Thread(){
             public void run() {
                 try {
                    // do thread 1....
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
             };
         }.start();
          
         new Thread(){
             public void run() {
                 try {
                     // do thread 2....
                     latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
             };
         }.start();
          
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // after thread 1 & 2 all over
        ...
     }
```

# CyclicBarrier

回环栅栏，实现让一组线程等待至某个状态之后再全部同时执行，在其中任意一个线程未达到同步点，其他到达的线程均会被阻塞。、

主要函数：

```java
public CyclicBarrier(int parties, Runnable barrierAction) {
}
 
public CyclicBarrier(int parties) {
}

// 挂起线程，直到所有线程都到达barrier状态再同时执行后续任务
public int await() throws InterruptedException, BrokenBarrierException { };
// 等待至一定的时间，如果还有线程没有到达barrier状态就直接往后走
public int await(long timeout, TimeUnit unit)throws InterruptedException,BrokenBarrierException,TimeoutException { };
```

例子：

```java
public static class CyclicBarrierTest implements Runnable {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4, this);
        Executor executor = Executors.newFixedThreadPool(4);
        ConcurrentLinkedQueue<String> records = new ConcurrentLinkedQueue<>();

        @Override
        public void run() {
            for (String s : records) {
                System.out.println("  [" + s + "]");
            }
        }

        public void test() {
            for (int i = 0; i < 4; i++) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println(Thread.currentThread().getName() + " is working...");
                            records.add(Thread.currentThread().getName());
                            cyclicBarrier.await();
                            System.out.println(Thread.currentThread().getName() + " is worked!");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }

    }
```

result：

```xml
pool-1-thread-1 is working...
pool-1-thread-2 is working...
pool-1-thread-3 is working...
pool-1-thread-4 is working...
  [pool-1-thread-1]
  [pool-1-thread-2]
  [pool-1-thread-3]
  [pool-1-thread-4]
pool-1-thread-4 is worked!
pool-1-thread-2 is worked!
pool-1-thread-1 is worked!
pool-1-thread-3 is worked!
```

# Semaphore

控制同时访问的线程个数，通过 acquire() 获取一个许可，如果没有就等待，而 release() 释放一个许可。

```java
//参数permits表示许可数目，即同时可以允许多少线程进行访问
public Semaphore(int permits) {              
	sync = new NonfairSync(permits);
}
//这个多了一个参数fair表示是否是公平的，即等待时间越久的越先获取许可
public Semaphore(int permits, boolean fair) {    
    sync = (fair)? new FairSync(permits) : new NonfairSync(permits);
}

// 阻塞式
public void acquire() throws InterruptedException {  } 
public void acquire(int permits) throws InterruptedException { }
public void release() { }
public void release(int permits) { }

// 非阻塞式
public boolean tryAcquire() { };  
public boolean tryAcquire(long timeout, TimeUnit unit) throws InterruptedException { }; 
public boolean tryAcquire(int permits) { };
public boolean tryAcquire(int permits, long timeout, TimeUnit unit) throws InterruptedException { };
```

例子：

```java
public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < 8; i++) {
            new Worker(i, semaphore).start();
        }
    }

    static class Worker extends Thread {
        private int num;
        private Semaphore semaphore;

        Worker(int num, Semaphore semaphore) {
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println(this.num + " is working...");
                Thread.sleep((this.num + 1) * 2000);
                System.out.println(this.num + "finish work!");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
```

result：

```xml
0 is working...
2 is working...
1 is working...
3 is working...
4 is working...
0finish work!
5 is working...
1finish work!
6 is working...
2finish work!
7 is working...
3finish work!
4finish work!
5finish work!
6finish work!
7finish work!
```
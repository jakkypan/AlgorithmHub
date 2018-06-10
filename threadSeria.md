# 问题

编写两个线程，一个线程打印1,2....，另一个线程打印字母A~Z，打印顺序为1A2B3C……26Z，要求使用线程间的通信。


## 效果

![](./ThreadSeria/threadseria.gif)

## 解决方案

### 1、synchronized和wait/notify

synchronized保证线程之间的同步性，wait/notify保证线程之间的阻塞。这也是最能想到的。

### 2、lock/condition

这里lock就扮演了synchronized的作用，而condition直接就是wait/notify的封装。

### 3、Volatile

以volatile保证线程间的同步，并且以被volatile修饰的变量作为线程之间的阻塞条件。

### 4、AtomicBoolean

这个的原理和volatile一样

### 5、CyclicBarrier

回环栅栏的机制，具体见：[CyclicBarrier&Semaphore](CyclicBarrier&Semaphore.md)

### 6、Semaphore

信号量的机制，这里利用了Semaphore.acquire()本身会阻塞的特性。具体见：[CyclicBarrier&Semaphore](CyclicBarrier&Semaphore.md)

### 7、BlockingQueue

我这里采用capacity为1的ArrayBlockingQueue，利用了take()本身会阻塞的特性。代码里构建了2个ArrayBlockingQueue。

### handler

这个是android自己的一种机制了。写了2种方式的实现。




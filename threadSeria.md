# 问题

编写两个线程，一个线程打印1,2....，另一个线程打印字母A~Z，打印顺序为1A2B3C……26Z，要求使用线程间的通信。


## 效果


## 解决方案

### 1、synchronized和wait/notify

synchronized保证线程之间的同步性，wait/notify保证线程之间的阻塞。这也是最能想到的。

### 2、lock/condition

这里lock就扮演了synchronized的作用，而condition直接就是wait/notify的封装。


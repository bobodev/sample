
synchronized、ReentrantLock、volatile、线程池、多线程调试、线程集合类

```
1. 概述
2. 锁
2.1. 锁的分类
2.1.1. 公平锁/非公平锁
2.1.2. 可重入锁
2.1.3. 独享锁/共享锁
2.1.4. 互斥锁/读写锁
2.1.5. 乐观锁/悲观锁
2.1.6. 分段锁
2.1.7. 偏向锁/轻量级锁/重量级锁
2.1.8. 自旋锁
2.2. Synchronized
2.2.1. 三种应用方式
2.2.1.1. synchronized作用于实例方法
2.2.1.1. synchronized作用于静态方法
2.2.1.1. synchronized同步代码块
2.2.1. synchronized底层语义
2.2.1.1. Java对象头与Monitor
2.2.1.2. synchronized修饰代码块
2.2.1.3. synchronized修饰方法
2.2.2. Java虚拟机对synchronized的优化
2.2.3. 关于synchronized 可能需要了解的关键点
2.2.3.1. synchronized的可重入性
2.2.4. 线程中断与synchronized
2.2.4.1. 线程中断
2.2.4.2. 中断与synchronized
2.3. ReentrantLock
2.3.1. 为什么要用ReentrantLock
2.3.2. 要不要抛弃synchronized
2.3.3. Condition的一个例子
3. volatile
4. 线程池
5. 多线程调试
6. 线程相关集合类
7. 参考
```

# 1. 概述
本文主要讲述了多线程相关的部分内容。主要围绕锁、volatile、线程池、多线程调试、线程集合类方面展开。

# 2. 锁
## 2.1. 锁的分类
常见锁的的分类
* 公平锁/非公平锁
* 可重入锁
* 独享锁/共享锁
* 互斥锁/读写锁
* 乐观锁/悲观锁
* 分段锁
* 偏向锁/轻量级锁/重量级锁
* 自旋锁

### 2.1.1. 公平锁/非公平锁

公平锁：是指多个线程按照申请锁的顺序来获取锁。
非公平锁：与公平锁相反，非公平锁的优点在于吞吐量比公平锁大。
ReentrantLock：默认是非公平锁，通过构造函数指定。
Synchronized：也是一种非公平锁。由于其并不像ReentrantLock是通过AQS的来实现线程调度，所以并没有任何办法使其变成公平锁。

备注：[AQS](https://www.cnblogs.com/daydaynobug/p/6752837.html)定义了一套多线程访问共享资源的同步器框架

### 2.1.2. 可重入锁
可重入锁又名递归锁，是指在同一个线程在外层方法获取锁的时候，在进入内层方法会自动获取锁。可重入锁的一个好处是可一定程度避免死锁
ReentrantLock（Re entrant Lock重新进入锁）：可重入锁
Synchronized：可重入锁

```
synchronized void setA() throws Exception{
    Thread.sleep(1000);
    setB();
}

synchronized void setB() throws Exception{
    Thread.sleep(1000);
}
```
上面的代码就是一个可重入锁的一个特点，如果不是可重入锁的话，setB可能不会被当前线程执行，可能造成死锁。

### 2.1.3. 独享锁/共享锁
独享锁：该锁一次只能被一个线程所持有。
共享锁：该锁可被多个线程所持有。
Java ReentrantLock：独享锁。
Synchronized：独享锁
ReadWriteLock，其读锁是共享锁，其写锁是独享锁。
独享锁与共享锁也是通过AQS来实现的，通过实现不同的方法，来实现独享或者共享。

#### 2.1.4. 互斥锁/读写锁
上面讲的独享锁/共享锁就是一种广义的说法，互斥锁/读写锁就是具体的实现。
互斥锁在Java中的具体实现就是ReentrantLock
读写锁在Java中的具体实现就是ReadWriteLock

#### 2.1.5. 乐观锁/悲观锁
乐观锁与悲观锁不是指具体的什么类型的锁，而是指看待并发同步的角度。
悲观锁：不加锁的并发操作一定会出问题。
乐观锁：不加锁的并发操作是没有事情的。
悲观锁适合写操作非常多的场景，乐观锁适合读操作非常多的场景

#### 2.1.6. 分段锁
分段锁其实是一种锁的设计，并不是具体的一种锁
ConcurrentHashMap：通过分段锁的形式来实现高效的并发操作。
分段锁的设计目的是细化锁的粒度，当操作不需要更新整个数组的时候，就仅仅针对数组中的一项进行加锁操作。

#### 2.1.7. 偏向锁/轻量级锁/重量级锁
这三种锁是指锁的状态，并且是针对Synchronized。在Java 5通过引入锁升级的机制来实现高效Synchronized。这三种锁的状态是通过对象监视器在对象头中的字段来表明的。
偏向锁是指一段同步代码一直被一个线程所访问，那么该线程会自动获取锁。降低获取锁的代价。
轻量级锁是指当锁是偏向锁的时候，被另一个线程所访问，偏向锁就会升级为轻量级锁，其他线程会通过自旋的形式尝试获取锁，不会阻塞，提高性能。
重量级锁是指当锁为轻量级锁的时候，另一个线程虽然是自旋，但自旋不会一直持续下去，当自旋一定次数的时候，还没有获取到锁，就会进入阻塞，该锁膨胀为重量级锁。重量级锁会让其他申请的线程进入阻塞，性能降低。

#### 2.1.8. 自旋锁
自旋锁是指尝试获取锁的线程不会立即阻塞，而是采用循环的方式去尝试获取锁，这样的好处是减少线程上下文切换的消耗，缺点是循环会消耗CPU。
典型的自旋锁实现的例子，可以参考[自旋锁的实现](http://ifeve.com/java_lock_see1/)

### 2.2. Synchronized
#### 2.2.1. 三种应用方式
* 修饰实例方法，作用于当前实例加锁，进入同步代码前要获得当前实例的锁
* 修饰静态方法，作用于当前类对象加锁，进入同步代码前要获得当前类对象的锁
* 修饰代码块，指定加锁对象，对给定对象加锁，进入同步代码库前要获得给定对象的锁。

##### 2.2.1.1. synchronized作用于实例方法
代码：参考 AccountingSync

没问题，因为对于一个实例，synchronized修饰的方法一个线程只能获得一把锁

代码：参考 AccountingSyncBad

有问题，因此t1和t2都会进入各自的对象锁，也就是说t1和t2线程使用的是不同的锁，因此线程安全是无法保证的。
如何解决这个问题？参见下面 2.2.1.1. synchronized作用于静态方法

##### 2.2.1.1. synchronized作用于静态方法
代码：参考 AccountingSyncClass

##### 2.2.1.1. synchronized同步代码块
同步代码块可以提高性能
代码：参考 AccountingSyncForBlock

#### 2.2.1. synchronized底层语义
Java 虚拟机中的同步(Synchronization)基于进入和退出管程(Monitor)对象实现， 无论是显式同步(有明确的 monitorenter 和 monitorexit 指令,即同步代码块)还是隐式同步都是如此。
不同点：
* synchronized修饰代码块（显示同步，由 monitorenter 和 monitorexit 指令来实现同步）
* synchronized修饰方法（隐式同步，由方法调用指令读取运行时常量池中方法的 ACC_SYNCHRONIZED 标志来隐式实现）

##### 2.2.1.1. Java对象头与Monitor
这块大家可以参考其他资料，简单来说
* 每个对象关联一个monitor，monitor是由ObjectMonitor实现的
* monitor对象存在于每个Java对象的对象头中(存储的指针的指向)
* synchronized锁便是通过这种方式获取锁的，也是为什么Java中任意对象可以作为锁的原因

##### 2.2.1.2. synchronized修饰代码块
现在我们重新定义一个synchronized修饰的同步代码块，在代码块中操作共享变量i，代码：SyncCodeBlock
```
package com.sample.thread.sync;

public class SyncCodeBlock {

   public int i;

   public void syncTask(){
       //同步代码库
       synchronized (this){
           i++;
       }
   }
}
```
编译后查看class
```
 3: monitorenter  //进入同步方法
//..........省略其他  
15: monitorexit   //退出同步方法
16: goto          24
//省略其他.......
21: monitorexit //退出同步方法
```
##### 2.2.1.3. synchronized修饰方法
```
package com.sample.thread.sync;

public class SyncMethod {

   public int i;

   public synchronized void syncTask(){
           i++;
   }
}
```
查看class文件
```
   descriptor: ()V
    //方法标识ACC_PUBLIC代表public修饰，ACC_SYNCHRONIZED指明该方法为同步方法
    flags: ACC_PUBLIC, ACC_SYNCHRONIZED
    Code:
      stack=3, locals=1, args_size=1
```
#### 2.2.2. Java虚拟机对synchronized的优化
自行查找，不展开

#### 2.2.3. 关于synchronized 可能需要了解的关键点
##### 2.2.3.1. synchronized的可重入性
* 获取到当前实例锁的方法A调用当前实例另外一个synchronized修饰的方法B，方法B再次请求实例锁时将被允许。
* 子类继承父类时，可以自动获得父类的实例锁
#### 2.2.4. 线程中断与synchronized
##### 2.2.4.1. 线程中断
正如中断二字所表达的意义，在线程运行(run方法)中间打断它，在Java中，提供了以下3个有关线程中断的方法

```
//中断线程（实例方法）
public void Thread.interrupt();

//判断线程是否被中断（实例方法）
public boolean Thread.isInterrupted();

//判断是否被中断并清除当前中断状态（静态方法）
public static boolean Thread.interrupted();
```

* 线程处于被阻塞状态或者试图执行一个阻塞操作
调用Thread.interrupt()抛出一个InterruptedException的异常，同时中断状态将会被复位(由中断状态改为非中断状态)，参考：InterruputSleepThread3

```
        /**
         * 输出结果:
           Interruted When Sleep
           interrupt:false
         */
```
* 线程处于处于运行期且非阻塞的状态
调用Thread.interrupt()中断线程是不会得到任响应的。代码： InterruputThread
```
        /**
         * 输出结果(无限执行):
             未被中断
             未被中断
             未被中断
             ......
         */
```
如何处理？手动进行检测，代码：InterruputThread2
* 兼顾两种方式
```
public void run(){
    try {
    //判断当前线程是否已中断,注意interrupted方法是静态的,执行后会对中断状态进行复位
    while (!Thread.interrupted()) {
        TimeUnit.SECONDS.sleep(2);
    }
    } catch (InterruptedException e) {

    }
}
```
##### 2.2.4.2. 中断与synchronized
线程中断操作对于正在等待获取的锁对象的synchronized方法或者代码块并不起作用，也就是对于synchronized来说，如果一个线程在等待锁，那么结果只有两种，要么它获得这把锁继续执行，要么它就保存等待，即使调用中断线程的方法，也不会生效。代码：SynchronizedBlocked

代码解析：
我们在SynchronizedBlocked构造函数中创建一个新线程并启动获取调用f()获取到当前实例锁，由于SynchronizedBlocked自身也是线程，启动后在其run方法中也调用了f()，但由于对象锁被其他线程占用，导致t线程只能等到锁，此时我们调用了t.interrupt();但并不能中断线程。

### 2.3. ReentrantLock
#### 2.3.1. 为什么要用ReentrantLock 
* synchronized存在一些限制，比如无法中断一个正在等候获得锁的线程，也无法通过轮询得到锁，如果不想等下去，也就没法得到锁。
* 存在一些更细力度的场景，ReentrantLock显得更灵活
* ReentrantLock的伸缩性更好，有实验表明，使用ReentrantLock，处理器大量的时间都花在处理实际计算上，非常少的一部分用在线程调度上。高吞吐率，CPU利用高效
* ReentrantLock 可以实现公平锁，虽然这在大部分场景下并不需要
* 基于conditon条件变量是实现线程阻塞唤醒，可以简化并发算法的开发。

#### 2.3.2. 要不要抛弃synchronized
* java.util.concurrent.lock 中的锁定类是用于高级用户和高级情况的工具 。一般来说，除非您对 Lock 的某个高级特性有明确的需要，或者有明确的证据（而不是仅仅是怀疑）表明在特定情况下，同步已经成为可伸缩性的瓶颈，否则还是应当继续使用 synchronized。
* synchronized 一些优势比如，在使用 synchronized 的时候，不可能忘记释放锁；在退出 synchronized 块时，JVM 会为您做这件事。您很容易忘记用 finally 块释放锁，这对程序非常有害。您的程序能够通过测试，但会在实际工作中出现死锁，那时会很难指出原因（这也是为什么根本不让初级开发人员使用 Lock 的一个好理由。）
* 当 JVM 用 synchronized 管理锁定请求和释放时，JVM 在生成线程转储时能够包括锁定信息。这些对调试非常有价值，因为它们能标识死锁或者其他异常行为的来源。
* synchronized是给予JVM实现的，可以在 JVM 的所有版本中工作，在 JDK 5.0 成为标准之前，使用 Lock 类将意味着要利用的特性不是每个 JVM 都有的，而且不是每个开发人员都熟悉的

#### 2.3.3. Condition的一个例子
代码：Queue

参考：http://blog.jobbole.com/104902/


## 3. volatile
参考一：http://ifeve.com/%E4%BD%A0%E5%BA%94%E8%AF%A5%E7%9F%A5%E9%81%93%E7%9A%84-volatile-%E5%85%B3%E9%94%AE%E5%AD%97/
补充见参考3
## 4. 线程池
## 5. 多线程调试
## 6. 线程相关集合类
## 7. 参考
1. [深入理解Java并发之synchronized实现原理](https://blog.csdn.net/javazejian/article/details/72828483)
2. [新的锁定类提高了同步性 —— 但还不能现在就抛弃 synchronized](https://www.ibm.com/developerworks/cn/java/j-jtp10264/index.html)
3.[深入理解Java内存模型（四）——volatile](http://www.infoq.com/cn/articles/java-memory-model-4)

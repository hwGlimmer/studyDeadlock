package com.glimmer.lock;

/**
 * 死锁：当有两个或更多的线程在等待对方释放锁并无限期地卡住时，这种情况就称为死锁。
 * 
 * 死锁产生的必要条件？
 * 
 * （1） 互斥：一次只有一个进程可以使用一个资源。其他进程不能访问已分配给其他进程的资源。
 * 
 * （2）占有且等待：当一个进程在等待分配得到其他资源时，其继续占有已分配得到的资源
 * 
 * （3）非抢占：不能强行抢占进程中已占有的资源。
 * 
 * （4）循环等待：存在一个封闭的进程链，使得每个资源至少占有此链中下一个进程所需要的一个资源。
 * 
 * @author Glimmer
 *
 */
public class deadLockTest {
	
	public static void main(String[] args) {
		DeadLockMethod t1 = new DeadLockMethod(true);
		t1.setName("死锁测试-线程1");
		t1.start();
		DeadLockMethod t2 = new DeadLockMethod(false);
		t2.setName("死锁测试-线程2");
		t2.start();
	}
}

/**
 * 模拟死锁： 两个线程互相等对方释放对象锁，才能进行下去
 * @author Glimmer
 *
 */
class DeadLockMethod extends Thread{
	
	// 用来调用两个方法的标识
	boolean lockFormer; 
	
	public DeadLockMethod(boolean lockFormer) {
		super();
		this.lockFormer = lockFormer;
	}

	// 模拟资源
	static Object o1 = new Object(); 
	static Object o2 = new Object();
	
	@Override
	public void run() {
		
		if (this.lockFormer) {
			// 锁定1后要继续锁定2才能执行下去
			synchronized (o1) {
				try {
					Thread.sleep(2000);
					System.out.println(Thread.currentThread().getName()+" :获得O1资源锁，尝试获取O2资源锁...");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (o2) {
					System.out.println(Thread.currentThread().getName()+" :获得O1资源锁，获得O2资源锁");
				}
			}
		}else {
			// 锁定2后要继续锁定1才能执行下去
			synchronized (o2) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()+" :获得O2资源锁，尝试获取O1资源锁...");
				synchronized (o1) {
					System.out.println(Thread.currentThread().getName()+" :获得O2资源锁，获得O1资源锁");
				}
			}
		}
	}
}

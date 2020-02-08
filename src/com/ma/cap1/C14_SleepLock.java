package com.ma.cap1;
/**
 * sleep 不会释放锁
 * @author mgh_2
 *
 */
public class C14_SleepLock {
	private Object lock = new Object();
	
	private class SleepLock implements Runnable {
		@Override
		public void run() {
			try {
				String name = Thread.currentThread().getName();
				synchronized (lock) {
					System.out.println(name+" taking the lock");
					Thread.sleep(2000);
					System.out.println(name+" finish the work");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private class SleepNotLock implements Runnable {
		@Override
		public void run() {
			String name = Thread.currentThread().getName();
			synchronized (lock) {
				System.out.println(name+" taking the lock");
				System.out.println(name+" finish the work");
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		C14_SleepLock lock = new C14_SleepLock();
		new Thread(lock.new SleepLock(),"SleepLock").start();
		System.out.println("this is " + Thread.currentThread().getName() + " thread");
		new Thread(lock.new SleepNotLock(),"SleepNotLock").start();
	}
}

package com.ma.cap4;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class C01_UseLock {

	private Lock lock = new ReentrantLock();
	private int sum;

	private class TestLock implements Runnable {
		@Override
		public void run() {
			try {
				System.out.println(Thread.currentThread().getName() + " is start");
				lock.lock();
				Thread.sleep(1000);
				System.out.println(Thread.currentThread().getName() + " is running");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}

	public synchronized void sum(int i) {//可重入锁
		if (i < 100) {
			i++;
			sum += i;
			sum(i);
		}
	}

	public static void main(String[] args) {

		C01_UseLock c01_UseLock = new C01_UseLock();
		TestLock testLock = c01_UseLock.new TestLock();
		for (int i = 0; i < 8; i++) {
			new Thread(testLock).start();
		}
		c01_UseLock.sum(0);
		System.out.println(c01_UseLock.sum);
	}
}

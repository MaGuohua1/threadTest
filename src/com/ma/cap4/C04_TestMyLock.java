package com.ma.cap4;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class C04_TestMyLock {
	
	private void test() {
		Lock lock = new ReentrantLock();
		Worker worker = new Worker(lock);
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(worker);
			thread.setDaemon(true);
			thread.start(); 
		}
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(1000);
				System.out.println("---------");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	private class Worker implements Runnable {
		
		private Lock lock;
		
		public Worker(Lock lock) {
			this.lock = lock;
		}

		@Override
		public void run() {
			lock.lock();
			try {
				Thread.sleep(1000);
				System.out.println(Thread.currentThread().getName());
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		C04_TestMyLock test = new C04_TestMyLock();
		test.test();
	}
	
}

package com.ma.cap1;

/**
 * volatile不能保证原子性，只能保证可见性
 * 
 * @author mgh_2
 *
 */
public class C9_VolatileUnsafe {

	private static class Volatile implements Runnable {

		private volatile int age = 0;

		@Override
		public void run() {
			age += 1;
			System.out.println(Thread.currentThread().getName() + " : " + age);
		}
	}

	public static void main(String[] args) {
		Volatile v = new Volatile();

		Thread thread1 = new Thread(v);
		Thread thread2 = new Thread(v);
		Thread thread3 = new Thread(v);
		Thread thread4 = new Thread(v);
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();

		try {
			thread1.join();
			thread2.join();
			thread3.join();
			thread4.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(v.age);
	}
}

package com.ma.cap1;

/**
 * 线程A执行了线程B的join(),则线程A必须等到线程B执行完成后在执行
 * 
 * @author mgh_2
 *
 */
public class C13_UseJoin {

	private static class UseJoin implements Runnable {
		@Override
		public void run() {
			try {
				long time = 2000;
				System.out.println("sleep " + time + " milliseconds");
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(new UseJoin());
		thread.start();
		thread.join();
		System.out.println("this is " + Thread.currentThread().getName() + " thread");
	}
}

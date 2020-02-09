package com.ma.cap2;

import java.util.concurrent.CountDownLatch;

public class C04_UseCountDownLatch {

	private final int LENGTH = 5;
	private CountDownLatch latch = new CountDownLatch(LENGTH);

	private class initThread implements Runnable {
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + " is init");
			latch.countDown();
		}
	}

	private class BusuThread implements Runnable {
		@Override
		public void run() {
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " is business");
		}
	}

	public static void main(String[] args) throws InterruptedException {
		C04_UseCountDownLatch useCountDownLatch = new C04_UseCountDownLatch();
		initThread init = useCountDownLatch.new initThread();
		new Thread(useCountDownLatch.new BusuThread()).start();
		Thread.sleep(1000);
		for (int i = 0; i < useCountDownLatch.LENGTH; i++) {
			new Thread(init).start();
		}
	}
}

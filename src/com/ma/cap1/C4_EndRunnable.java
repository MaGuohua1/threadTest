package com.ma.cap1;

public class C4_EndRunnable {

	private static class UseRunnable implements Runnable {

		@Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				String name = Thread.currentThread().getName();
				System.out.println("this is a UseRunnable implements Runnable, the name is " + name);
			}
			System.out.println("interrupt flag is " + Thread.currentThread().isInterrupted());
		}

	}

	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(new UseRunnable());
		thread.start();
		Thread.sleep(20);
		thread.interrupt();
	}
}

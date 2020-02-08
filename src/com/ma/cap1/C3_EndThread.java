package com.ma.cap1;

/**
 * 如何安全的中断线程 java 线程是协作式处理的
 * 
 * @author mgh_2
 *
 */
public class C3_EndThread {

	private static class UseThread extends Thread {

		@Override
		public void run() {
			while (!isInterrupted()) {
				String name = Thread.currentThread().getName();
				System.out.println("this is a UseThread extends Thread, the name is " + name);
			}
			System.out.println("interrupt flag is " + isInterrupted());
		}

	}

	public static void main(String[] args) throws InterruptedException {
		UseThread thread = new UseThread();
		thread.start();
		Thread.sleep(20);
		thread.interrupt();
	}
}

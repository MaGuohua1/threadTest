package com.ma.cap1;

/**
 * 线程中有InterruptedException时，会中断失败，需要在cathe中中断
 * 
 * @author mgh_2
 *
 */
public class C5_HasInterruptEexception {

	private static class UseThread extends Thread {

		@Override
		public void run() {
			while (!isInterrupted()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.out.println("exception interrupt flag is " + isInterrupted());
					interrupt();
					e.printStackTrace();
				}
				String name = Thread.currentThread().getName();
				System.out.println("this is a UseThread extends Thread, the name is " + name);
			}
			System.out.println("interrupt flag is " + isInterrupted());
		}

	}

	public static void main(String[] args) throws InterruptedException {
		UseThread thread = new UseThread();
		thread.start();
		Thread.sleep(500);
		thread.interrupt();
	}
}

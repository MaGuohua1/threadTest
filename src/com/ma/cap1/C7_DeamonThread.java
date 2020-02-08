package com.ma.cap1;

/**
 * 守护线程跟随主线程死亡 如果守护线程有try..finally时，finally语句不一定执行
 * 
 * @author mgh_2
 *
 */
public class C7_DeamonThread {

	private static class UseThread extends Thread {

		@Override
		public void run() {
			try {
				while (!isInterrupted()) {
					System.out.println(getName() + " instanceof Thread");
				}
				System.out.println(getName() + "interrupt flag is " + isInterrupted());
			} finally {
				System.out.println("=============finally");
			}
		}

	}

	public static void main(String[] args) throws InterruptedException {
		UseThread thread = new UseThread();
		thread.setDaemon(true);
		thread.start();
		Thread.sleep(5);
	}
}

package com.ma.cap1;

/**
 * 调用run和线程的区别
 * 
 * @author mgh_2
 *
 */
public class C6_StartAndRun {

	private static class ThreadRun extends Thread {

		@Override
		public void run() {
			int i = 10;
			while (i > 0) {
				try {
					Thread.sleep(1000);
					System.out.println("I am " + Thread.currentThread().getName() + " and now the i = " + i--);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void main(String[] args) {
		ThreadRun run = new ThreadRun();
		run.setName("run");
//		run.run();//普通方法
		run.start();// 启动线程
	}
}

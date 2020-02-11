package com.ma.cap7;

/**
 * 普通的死锁
 * 
 * @author mgh_2
 *
 */
public class C01_NormalDeadLock {

	private Object firstLock = new Object();
	private Object secondLock = new Object();

	private void firstToSecond() {
		synchronized (firstLock) {
			System.out.println(Thread.currentThread().getName() + " get firstlock, and try to get the secondLock");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			secondToFirst();
		}
	}

	private void secondToFirst() {
		synchronized (secondLock) {
			System.out.println(Thread.currentThread().getName() + " get secondLock, and try to get the firstlock");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			firstToSecond();
		}
	}

	private class testThread extends Thread {

		@Override
		public void run() {
			firstToSecond();
		}

	}

	public static void main(String[] args) {
		C01_NormalDeadLock c01_NormalDeadLock = new C01_NormalDeadLock();
		c01_NormalDeadLock.new testThread().start();
		c01_NormalDeadLock.secondToFirst();
	}
}

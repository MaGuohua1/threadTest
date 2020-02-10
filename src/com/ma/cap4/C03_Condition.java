package com.ma.cap4;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class C03_Condition {
	private int km;
	private String site;
	private Lock lock = new ReentrantLock();
	private Condition kmCond = lock.newCondition();
	private Condition siteCond = lock.newCondition();

	public C03_Condition(int km, String site) {
		this.km = km;
		this.site = site;
	}

	private void changeKm() {
		lock.lock();
		try {
			km = 101;
			kmCond.signal();
		} finally {
			lock.unlock();
		}
	}

	private void waitKm() {
		lock.lock();
		try {
			while (km <= 100) {
				try {
					System.out.println(Thread.currentThread().getName());
					kmCond.await(3, TimeUnit.SECONDS);
//					kmCond.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("the km is " + km + " km,I will change db.");
		} finally {
			lock.unlock();
		}
	}

	private void changeSite() {
		lock.lock();
		try {
			site = "string";
			siteCond.signal();
		} finally {
			lock.unlock();
		}
	}

	private void waitSite() {
		lock.lock();
		try {
			while ("".equals(site)) {
				try {
					System.out.println(Thread.currentThread().getName());
					siteCond.await(3, TimeUnit.SECONDS);
//					siteCond.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("the site is " + site + ",I will change db.");
		} finally {
			lock.unlock();
		}
	}

	private static class TestCond implements Runnable {
		private C03_Condition condition;

		public TestCond(C03_Condition condition) {
			this.condition = condition;
		}

		@Override
		public void run() {
			condition.waitKm();
		}
	}

	private static class TestCond1 implements Runnable {
		private C03_Condition condition;

		public TestCond1(C03_Condition condition) {
			this.condition = condition;
		}

		@Override
		public void run() {
			condition.waitSite();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		C03_Condition condition = new C03_Condition(0, "");
		TestCond km = new TestCond(condition);
		TestCond1 site = new TestCond1(condition);
		for (int i = 0; i < 3; i++) {
			new Thread(km, "km" + i).start();
			new Thread(site, "site" + i).start();
		}
		Thread.sleep(1000);
		condition.changeKm();
		Thread.sleep(1000);
		condition.changeSite();
	}
}

package com.ma.cap1;

public class C10_WaitAndNotify {

	private int km;
	private String site;

	public C10_WaitAndNotify(int km, String site) {
		this.km = km;
		this.site = site;
	}

	private synchronized void changeKm() {
		km = 101;
		notifyAll();
	}

	private synchronized void waitKm() {
		while (km <= 100) {
			try {
				System.out.println(Thread.currentThread().getName());
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("the km is " + km + " km,I will change db.");
	}

	private synchronized void changeSite() {
		site = "string";
		notifyAll();
	}

	private synchronized void waitSite() {
		while ("".equals(site)) {
			try {
				System.out.println(Thread.currentThread().getName());
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("the site is " + site + ",I will change db.");
	}

	private static class TestWait implements Runnable {
		private C10_WaitAndNotify waitAndNotify;

		public TestWait(C10_WaitAndNotify waitAndNotify) {
			this.waitAndNotify = waitAndNotify;
		}

		@Override
		public void run() {
			waitAndNotify.waitKm();
		}
	}

	private static class TestWait1 implements Runnable {
		private C10_WaitAndNotify waitAndNotify;

		public TestWait1(C10_WaitAndNotify waitAndNotify) {
			this.waitAndNotify = waitAndNotify;
		}

		@Override
		public void run() {
			waitAndNotify.waitSite();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		C10_WaitAndNotify waitAndNotify = new C10_WaitAndNotify(0, "");
		for (int i = 0; i < 3; i++) {
			new Thread(new TestWait(waitAndNotify), "km" + i).start();
			new Thread(new TestWait1(waitAndNotify), "site" + i).start();
		}
		Thread.sleep(3000);
		waitAndNotify.changeKm();
		Thread.sleep(3000);
		waitAndNotify.changeSite();
	}
}

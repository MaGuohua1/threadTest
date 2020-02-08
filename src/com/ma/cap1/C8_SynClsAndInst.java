package com.ma.cap1;

public class C8_SynClsAndInst {

	// 使用类锁的线程
	private static class SynClass extends Thread {
		@Override
		public void run() {
			System.out.println("test class is runing...");
			clsSyn();
		}
	}

	// 对象锁的线程
	private static class InstanceSyn implements Runnable {
		private C8_SynClsAndInst synClsAndInst;

		public InstanceSyn(C8_SynClsAndInst synClsAndInst) {
			this.synClsAndInst = synClsAndInst;
		}

		@Override
		public void run() {
			System.out.println("test class is runing..." + synClsAndInst);
			synClsAndInst.instSyn();
		}
	}

	private static class InstanceSyn2 implements Runnable {
		private C8_SynClsAndInst synClsAndInst;

		public InstanceSyn2(C8_SynClsAndInst synClsAndInst) {
			this.synClsAndInst = synClsAndInst;
		}

		@Override
		public void run() {
			System.out.println("test class is runing..." + synClsAndInst);
			synClsAndInst.instSyn1();
		}
	}

	// 类锁
	private static synchronized void clsSyn() {
		System.out.println("this is class synchronized going");
	}

	// 对象锁
	private synchronized void instSyn() {
		try {
			Thread.sleep(3);
			System.out.println("this is instance synchronized going");
			Thread.sleep(3);
			System.out.println("this is instance end");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void instSyn1() {
		synchronized (this) {
			try {
				Thread.sleep(3);
				System.out.println("this is instance1 synchronized going");
				Thread.sleep(3);
				System.out.println("this is instance1 end");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		// 对不同的对象加对象锁
//		new Thread(new InstanceSyn(new C8_SynClsAndInst())).start();
//		new Thread(new InstanceSyn2(new C8_SynClsAndInst())).start();

		// 对同一对象加锁
		C8_SynClsAndInst synClsAndInst = new C8_SynClsAndInst();
		new Thread(new InstanceSyn(synClsAndInst)).start();
		new Thread(new InstanceSyn2(synClsAndInst)).start();

		// 类锁
		new SynClass().start();

	}
}

package com.ma.cap1;

/**
 * ThreadLocal线程间变量相互独立
 * 
 * @author mgh_2
 *
 */
public class C11_UseThreadLocal {

	static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
		@Override
		protected Integer initialValue() {
			return 1;
		}
	};

	/* 测试线程，线程的工作是将ThreadLocal变量的值的变化 */
	private static class testThread implements Runnable {
		int id;

		public testThread(int id) {
			this.id = id;
		}

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + ": start " + id);
			id += threadLocal.get();
			threadLocal.set(id);
			System.out.println(Thread.currentThread().getName() + ": start " + id);
		}
	}

	public void StartThreadArray() {
		Thread[] runs = new Thread[3];
		for (int i = 0; i < runs.length; i++) {
			runs[i] = new Thread(new testThread(i));
		}
		for (int i = 0; i < runs.length; i++) {
			runs[i].start();
		}
	}

	public static void main(String[] args) {
		C11_UseThreadLocal local = new C11_UseThreadLocal();
		local.StartThreadArray();
	}
}

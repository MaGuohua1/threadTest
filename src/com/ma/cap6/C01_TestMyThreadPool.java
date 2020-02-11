package com.ma.cap6;

public class C01_TestMyThreadPool {

	public static void main(String[] args) throws InterruptedException {
		C01_MyThreadPool pool = new C01_MyThreadPool();
		for (int i = 0; i < 5; i++) {
			pool.execute(new C01_Worker("worker" + i));
		}
		System.out.println(pool);
		Thread.sleep(1000);
		pool.destory();
		System.out.println(pool);
	}
}

class C01_Worker implements Runnable {
	private String name;

	public C01_Worker(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		System.out.println(name);
	}
}
package com.ma.cap3;

import java.util.concurrent.atomic.AtomicInteger;

public class C01_UseAtomicInteger {

	static AtomicInteger integer = new AtomicInteger();

	private static class Test implements Runnable {
		@Override
		public void run() {
			System.out.println(integer.incrementAndGet());
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Test test = new Test();
		for (int i = 0; i < 30; i++) {
			new Thread(test).start();
		}
		Thread.sleep(1000);
		System.out.println(integer.getAndIncrement());
		System.out.println(integer.incrementAndGet());
		System.out.println(integer.get());
	}
}

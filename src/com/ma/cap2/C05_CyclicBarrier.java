package com.ma.cap2;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class C05_CyclicBarrier {

	private final int LENGTH = 5;
	private CyclicBarrier barrier;

	private class initThread implements Runnable {
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + " is init before");
			try {
				barrier.await();// （栅栏）所有的await都执行后才开启栅栏，执行后面的代码
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " is init after");
		}
	}

	// 栅栏开放后执行
	private class BusiThread implements Runnable {
		@Override
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " is business");
		}
	}

	public static void main(String[] args) throws InterruptedException {
		C05_CyclicBarrier cyclic = new C05_CyclicBarrier();
		cyclic.barrier = new CyclicBarrier(cyclic.LENGTH, cyclic.new BusiThread());
		initThread initThread = cyclic.new initThread();
		Thread.sleep(1000);
		for (int i = 0; i < cyclic.LENGTH; i++) {
			new Thread(initThread).start();
		}
	}
}

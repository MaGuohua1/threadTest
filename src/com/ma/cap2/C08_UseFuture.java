package com.ma.cap2;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class C08_UseFuture {

	private class UseCallable implements Callable<Integer>{
		@Override
		public Integer call() throws Exception {
			System.out.println("Callable 子线程计算开始");
			int sum = 0;
			Thread.sleep(2000);
			for (int i = 0; i < 5000; i++) {
				sum += i;
			}
			System.out.println("Callable 子线程计算完成");
			return sum;
		}
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		UseCallable callable = new C08_UseFuture().new UseCallable();
		FutureTask<Integer> task = new FutureTask<Integer>(callable);
		new Thread(task).start();
		if (new Random().nextBoolean()) {
			System.out.println(task.get());
		} else {
			System.out.println("中断");
			task.cancel(true);
		}
		System.out.println(task.isCancelled());
	}
}

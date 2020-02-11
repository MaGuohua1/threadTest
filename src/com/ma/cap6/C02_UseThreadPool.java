package com.ma.cap6;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class C02_UseThreadPool {

	private static class Worker implements Runnable {
		private String taskName;
		private Random random = new Random();

		public Worker(String taskName) {
			this.taskName = taskName;
		}

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + " process the task : " + taskName);
			try {
				Thread.sleep(random.nextInt(100) * 5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static class CallWorker implements Callable<String> {
		private String taskName;
		private Random random = new Random();

		public CallWorker(String taskName) {
			this.taskName = taskName;
		}

		@Override
		public String call() {
			System.out.println(Thread.currentThread().getName() + " process the task : " + taskName);
			return Thread.currentThread().getName() + ":" + random.nextInt(100) * 5;
		}
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executor = new ThreadPoolExecutor(2, 4, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10),
				new ThreadPoolExecutor.DiscardOldestPolicy());
//		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 0; i < 50; i++) {
			executor.execute(new Worker("runnable" + i));
			Future<String> submit = executor.submit(new CallWorker("callble" + i));
			System.out.println(submit.get());
		}
		executor.shutdown();
	}
}

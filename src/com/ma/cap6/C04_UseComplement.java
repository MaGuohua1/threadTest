package com.ma.cap6;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class C04_UseComplement {

	private static final int POOL_SIZE = Runtime.getRuntime().availableProcessors();
	private static final int TASK_COUNT = Runtime.getRuntime().availableProcessors() * 10;

	public static void main(String[] args) {
		C04_UseComplement complement = new C04_UseComplement();
		complement.testByQueue();
		complement.testByComplement();
	}

	private void testByComplement() {
		long start = System.currentTimeMillis();
		ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
		CompletionService<Info> service = new ExecutorCompletionService<Info>(pool);
		AtomicInteger integer = new AtomicInteger();

		for (int i = 0; i < TASK_COUNT; i++) {
			service.submit(new Task("task_" + i));
		}

		for (int i = 0; i < TASK_COUNT; i++) {
			try {
				Info info = service.take().get();
				System.out.println(info.getName() + ":" + info.getTime());
				integer.addAndGet(info.getTime());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

		pool.shutdown();
		System.out.println("tasks sleep time " + integer.get() + " ms, and spend time "
				+ (System.currentTimeMillis() - start) + " ms");
	}

	private void testByQueue() {
		long start = System.currentTimeMillis();
		ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
		BlockingQueue<Future<Info>> queue = new LinkedBlockingQueue<>();
		AtomicInteger integer = new AtomicInteger();

		for (int i = 0; i < TASK_COUNT; i++) {
			Future<Info> future = pool.submit(new Task("task_" + i));
			queue.add(future);
		}

		for (int i = 0; i < TASK_COUNT; i++) {
			try {
				Info info = queue.take().get();
				System.out.println(info.getName() + ":" + info.getTime());
				integer.addAndGet(info.getTime());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

		pool.shutdown();
		System.out.println("tasks sleep time " + integer.get() + " ms, and spend time "
				+ (System.currentTimeMillis() - start) + " ms");
	}
}

class Task implements Callable<Info> {
	private String name;

	public Task(String name) {
		this.name = name;
	}

	@Override
	public Info call() {
		int time = new Random().nextInt(1000);
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Info info = new Info();
		info.setName(name);
		info.setTime(time);
		return info;
	}

}

class Info {
	private String name;
	private int time;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
}
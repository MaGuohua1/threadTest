package com.ma.cap1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 
 * @author mgh_2
 *
 */
public class C2_RunnableAndCallable {

	/* 实现Runnable接口 */
	private static class UseRun implements Runnable {

		@Override
		public void run() {
			System.out.println("I am implements Runnable");
		}

	}

	/* 实现Callable接口，允许由返回值 */
	private static class UseCall implements Callable<String> {

		@Override
		public String call() throws Exception {
			System.out.println("I am implements Callable");
			return "qwertyuiop";
		}

	}

	public static void main(String[] args) {
		UseRun run = new UseRun();
		Thread thread = new Thread(run);
		thread.start();

		FutureTask<String> future = new FutureTask<String>(new UseCall());
		new Thread(future).start();
		try {
			// get()方法阻塞的
			System.out.println(future.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}

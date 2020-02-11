package com.ma.cap6;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class C01_MyThreadPool {

	// 默认工作线程个数
	private static final int POOL_ZISE = 5;
	// 设置默认的任务长度
	private static final int TASK_COUNT = 100;

	private BlockingQueue<Runnable> queue;
	private WorkThread[] workThreads;

	public C01_MyThreadPool() {
		this(POOL_ZISE, TASK_COUNT);
	}

	public C01_MyThreadPool(int poolSize, int taskCount) {
		if (poolSize <= 0)
			poolSize = POOL_ZISE;
		if (taskCount <= 0)
			taskCount = TASK_COUNT;
		this.queue = new ArrayBlockingQueue<Runnable>(taskCount);
		workThreads = new WorkThread[poolSize];
		for (int i = 0; i < poolSize; i++) {
			workThreads[i] = new WorkThread();
			workThreads[i].start();
		}
	}

	// 执行任务，其实只是把任务加入任务队列，什么时候执行有线程管理器决定
	public void execute(Runnable runnable) {
		try {
			queue.put(runnable);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 销毁线程池，该方法保证在所有任务都完成的情况下才销毁线程，否则等待任务完成才销毁
	public void destory() {
		System.out.println("ready close pool....");
		for (int i = 0; i < workThreads.length; i++) {
			workThreads[i].stopWorker();
			workThreads[i] = null;
		}
		queue.clear();
		queue = null;
		System.out.println(queue);
	}

	private class WorkThread extends Thread {
		@Override
		public void run() {
			Runnable runnable = null;
			try {
				while (!isInterrupted()) {
					runnable = queue.take();
					if (runnable != null) {
						System.out.println(getName() + " is ready execute: " + runnable);
						runnable.run();
					}
					runnable = null;// help GC
				}
			} catch (InterruptedException e) {
			}
		}

		public void stopWorker() {
			interrupt();
		}
	}

}

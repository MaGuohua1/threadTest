package com.ma.cap8.damo;

import java.util.List;
import java.util.Random;

import com.ma.cap8.concurrentTaskFrameWork.PendingJobPool;
import com.ma.cap8.concurrentTaskFrameWork.TaskResult;

public class AppTest {

	private static final String JOB_NAME = "计算数值";
	private static final int JOB_LENGTH = 1000;

	private class QueryResult implements Runnable {
		private PendingJobPool pool;

		public QueryResult(PendingJobPool pool) {
			this.pool = pool;
		}

		@Override
		public void run() {
			while (true) {
				try {
					List<TaskResult<Object>> taskDetail = pool.getTaskDetail(JOB_NAME);
					if (taskDetail == null) {
						break;
					}
					if (!taskDetail.isEmpty()) {
						System.out.println(pool.getTotalProgress(JOB_NAME));
						System.out.println(taskDetail);
					}
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		AppTest test = new AppTest();
		test.test();
	}

	private void test() {
		MyTask task = new MyTask();
		PendingJobPool pool = PendingJobPool.getInstance();
		pool.register(JOB_NAME, JOB_LENGTH, task, 3000);
		Random r = new Random();
		for (int i = 0; i < JOB_LENGTH; i++) {
			pool.execute(JOB_NAME, r.nextInt(1000));
		}
		new Thread(new QueryResult(pool)).start();
	}
}

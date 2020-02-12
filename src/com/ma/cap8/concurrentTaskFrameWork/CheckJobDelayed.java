package com.ma.cap8.concurrentTaskFrameWork;

import java.util.concurrent.DelayQueue;

/**
 * 任务完成后，在一定的时间内查询，之后释放资源，节约内存，需要定期处理过期任务
 * 
 * @author mgh_2
 *
 */
public class CheckJobDelayed {

	private static DelayQueue<DelayedImpl<String>> queue;

	// 单例模式
	private CheckJobDelayed() {
		queue = new DelayQueue<DelayedImpl<String>>();
		Thread thread = new Thread(new FetchJob());
		thread.setDaemon(true);
		thread.start();
		System.out.println("start the deamon of CheckJobDelayed.");
	}

	private static class Instance {
		private static CheckJobDelayed instance = new CheckJobDelayed();
	}

	public static CheckJobDelayed getInstance() {
		return Instance.instance;
	}

	// 任务完成后，放入延时处理队列，经过expireTime时间后，从框架中移除
	public void putJob(String jobName, long expireTime) {
		DelayedImpl<String> job = new DelayedImpl<String>(jobName, expireTime);
		queue.offer(job);
		System.out.println(
				"Job [" + jobName + "] already put into CheckJobDelayed, and the timeout is " + expireTime + " ms.");
	}

	// 处理队列中到期的任务
	private class FetchJob implements Runnable {
		@Override
		public void run() {
			try {
				while (true) {
					DelayedImpl<String> jobInfo = queue.take();
					String jobName = jobInfo.getData();
					PendingJobPool.getJobs().remove(jobName);
					System.out.println(jobName + " already removed the map.");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

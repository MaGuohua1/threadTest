package com.ma.cap8.concurrentTaskFrameWork;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PendingJobPool {

	private static final int POOL_SIZE = Runtime.getRuntime().availableProcessors();
	private static final int CAPACITY = 5000;

	private static ThreadPoolExecutor pool;
	private static BlockingQueue<Runnable> queue;
	private static ConcurrentHashMap<String, JobInfo<?>> jobs;

	// 单例模式 start
	private PendingJobPool() {
		queue = new ArrayBlockingQueue<Runnable>(CAPACITY);
		pool = new ThreadPoolExecutor(POOL_SIZE, POOL_SIZE, 3, TimeUnit.SECONDS, queue);
		pool.allowCoreThreadTimeOut(true);
		jobs = new ConcurrentHashMap<String, JobInfo<?>>();
	}

	private static class Instance {
		private static PendingJobPool instance = new PendingJobPool();
	}

	public static PendingJobPool getInstance() {
		return Instance.instance;
	}

	public static ConcurrentHashMap<String, JobInfo<?>> getJobs() {
		return jobs;
	}

	// 注册工作
	public void register(String jobName, int jobLength, ITaskProcessor<?, ?> processor, long expireTime) {
		JobInfo<Object> jobInfo = new JobInfo<>(jobName, jobLength, processor, expireTime);
		if (jobs.putIfAbsent(jobName, jobInfo) != null) {
			throw new RuntimeException(jobName + " already registered.");
		}
	}

	// 提交
	public <R, T> void execute(String jobName, T task) {
		JobInfo<R> jobInfo = getJob(jobName);
		if (jobInfo == null) {
			throw new IllegalArgumentException(jobName + " is an illegal job.");
		}
		PendingTask<R, T> pendingTask = new PendingTask<R, T>(jobInfo, task);
		pool.execute(pendingTask);
	}

	@SuppressWarnings("unchecked")
	private <R> JobInfo<R> getJob(String jobName) {
		JobInfo<R> jobInfo = (JobInfo<R>) jobs.get(jobName);
		return jobInfo;
	}

	// 任务处理
	public class PendingTask<R, T> implements Runnable {
		JobInfo<R> jobInfo;
		T task;

		public PendingTask(JobInfo<R> jobInfo, T task) {
			this.jobInfo = jobInfo;
			this.task = task;
		}

		@Override
		@SuppressWarnings("unchecked")
		public void run() {
			ITaskProcessor<R, T> processor = (ITaskProcessor<R, T>) jobInfo.getProcessor();
			TaskResult<R> result = processor.taskExecute(task);
			try {
				if (result == null) {
					result = new TaskResult<R>(TaskResultType.Exception, null, "result is null");
				}
				if (result.getType() == null) {
					if (result.getReason() == null) {
						result = new TaskResult<R>(TaskResultType.Exception, null, "reason is null");
					} else {
						result = new TaskResult<R>(TaskResultType.Exception, null,
								"result is null,but reason is : " + result.getReason());
					}
				}
			} catch (Exception e) {
				result = new TaskResult<R>(TaskResultType.Exception, null, e.getMessage());
			} finally {
				jobInfo.addTaskResult(result);
			}
		}
	}

	// 获取任务详细信息
	public <R> List<TaskResult<R>> getTaskDetail(String jobName) {
		JobInfo<R> jobInfo = getJob(jobName);
		if (jobInfo == null) {
			return null;
		}
		return jobInfo.getTaskDetail();
	}

	// 获取任务总进度
	public <R> String getTotalProgress(String jobName) {
		JobInfo<R> jobInfo = getJob(jobName);
		if (jobInfo == null) {
			return null;
		}
		return jobInfo.getTotalProgress();
	}
}

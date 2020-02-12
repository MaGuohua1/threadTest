package com.ma.cap8.concurrentTaskFrameWork;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class JobInfo<R> {

	private final String jobName;
	private final int jobLength;
	private final ITaskProcessor<?, ?> processor;
	private LinkedBlockingQueue<TaskResult<R>> results;
	private AtomicInteger successCount;
	private AtomicInteger executeCount;
	private final long expireTime;

	public JobInfo(String jobName, int jobLength, ITaskProcessor<?, ?> processor, long expireTime) {
		this.jobName = jobName;
		this.jobLength = jobLength;
		this.processor = processor;
		this.results = new LinkedBlockingQueue<TaskResult<R>>(jobLength);
		this.successCount = new AtomicInteger();
		this.executeCount = new AtomicInteger();
		this.expireTime = expireTime;
	}

	public int getSuccessCount() {
		return successCount.get();
	}

	public int getExecuteCount() {
		return executeCount.get();
	}

	public int getFailureCount() {
		return getExecuteCount() - getSuccessCount();
	}

	public String getTotalProgress() {
		return jobName + " [ Success/Execute : " + getSuccessCount() + "/" + getExecuteCount() + ", Total : "
				+ jobLength + " ] ";
	}

	public ITaskProcessor<?, ?> getProcessor() {
		return processor;
	}

	public List<TaskResult<R>> getTaskDetail() {
		List<TaskResult<R>> list = new LinkedList<TaskResult<R>>();
		TaskResult<R> result;
		while ((result = results.poll()) != null) {
			list.add(result);
		}
		return list;
	}

	public void addTaskResult(TaskResult<R> result) {
		if (result.getType() == TaskResultType.Success) {
			successCount.incrementAndGet();
		}
		executeCount.incrementAndGet();
		results.offer(result);
		if (executeCount.get() == jobLength) {// 任务完成后，添加到到期队列，移除任务数据
			CheckJobDelayed delayed = CheckJobDelayed.getInstance();
			delayed.putJob(jobName, expireTime);
		}
	}
}

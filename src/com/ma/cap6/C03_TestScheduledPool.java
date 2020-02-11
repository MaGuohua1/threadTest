package com.ma.cap6;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class C03_TestScheduledPool {

	public static void main(String[] args) {
		ScheduledExecutorService pool = new ScheduledThreadPoolExecutor(1);
		pool.scheduleAtFixedRate(new C03_ScheduledWorker(C03_ScheduledWorker.NORMAL), 1000, 3000,
				TimeUnit.MILLISECONDS);
	}
}

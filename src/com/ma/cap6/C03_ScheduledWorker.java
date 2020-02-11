package com.ma.cap6;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时任务的工作类
 * 
 * @author mgh_2
 *
 */
public class C03_ScheduledWorker implements Runnable {

	public static final int NORMAL = 0;// 普通任务类型
	public static final int HAS_EXCEPTION = -1;// 会抛出异常的任务类型
	public static final int PROCESS_EXCEPTION = 1;// 抛出异常但会捕捉的任务类型

	public static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private int taskType;

	public C03_ScheduledWorker(int taskType) {
		this.taskType = taskType;
	}

	@Override
	public void run() {
		if (taskType == HAS_EXCEPTION) {
			System.out.println(format.format(new Date()) + " Exception made...");
			throw new RuntimeException("hasExcetion Happen");
		} else if (taskType == PROCESS_EXCEPTION) {
			try {
				System.out.println(format.format(new Date()) + " Exception made,but catch");
				throw new RuntimeException("ProcessException Happen");
			} catch (Exception e) {
				System.out.println("Exception be catched");
			}
		} else {
			System.out.println("Normal running: " + format.format(new Date()));
		}

	}

}

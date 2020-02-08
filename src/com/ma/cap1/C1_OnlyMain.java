package com.ma.cap1;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * Reference Handler：负责清除引用 Finalizer：负责调用finalize Signal Dispatcher:分发信号线程
 * Attach Listener：监听当前程序运行时相关的信息 java天生是多线程的
 * 
 * @author mgh_2
 *
 */
public class C1_OnlyMain {

	public static void main(String[] args) {
		// 虚拟机线程管理系统的接口
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		ThreadInfo[] dumpAllThreads = threadMXBean.dumpAllThreads(false, false);
		for (ThreadInfo threadInfo : dumpAllThreads) {
			System.out.println(threadInfo.getThreadId() + " : " + threadInfo.getThreadName());

		}
	}
}

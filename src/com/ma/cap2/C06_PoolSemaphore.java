package com.ma.cap2;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * C06_Semaphore信号量
 * 
 * @author mgh_2
 *
 */
public class C06_PoolSemaphore {

	private final int LENGTH = 10;
	private LinkedList<Conn> pool = new LinkedList<>();
	private Semaphore full, less;

	private class Conn {
	}

	public C06_PoolSemaphore() {
		for (int i = 0; i < LENGTH; i++) {
			pool.add(new Conn());
		}
		this.full = new Semaphore(LENGTH);
		this.less = new Semaphore(0);
	}

	private Conn fetchConn() throws InterruptedException {
		Conn conn = null;
		if (full.tryAcquire(3, TimeUnit.SECONDS)) {
			synchronized (pool) {
				conn = pool.pollFirst();
			}
			System.out.println(Thread.currentThread().getName() + " is get conn");
			less.release();
		} else {
			System.out.println(Thread.currentThread().getName() + " is not get conn");
		}
		return conn;
	}

	private void releaseConn(Conn conn) throws InterruptedException {
		if (conn == null)
			return;
		System.out.println(full.getQueueLength() + " thread is waiting the conn, and have " + full.availablePermits());
		less.acquire();
		synchronized (pool) {
			pool.addLast(conn);
		}
		System.out.println(Thread.currentThread().getName() + " is lost conn");
		full.release();
	}

	private class taskThread implements Runnable {
		C06_PoolSemaphore poolSemaphore;

		public taskThread(C06_PoolSemaphore poolSemaphore) {
			this.poolSemaphore = poolSemaphore;
		}

		@Override
		public void run() {
			Conn conn = null;
			try {
				conn = poolSemaphore.fetchConn();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				try {
					poolSemaphore.releaseConn(conn);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		C06_PoolSemaphore poolSemaphore = new C06_PoolSemaphore();
		taskThread thread = poolSemaphore.new taskThread(poolSemaphore);
		for (int i = 0; i < 50; i++) {
			new Thread(thread).start();
		}
	}
}

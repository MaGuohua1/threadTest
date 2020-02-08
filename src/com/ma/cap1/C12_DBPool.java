package com.ma.cap1;

/**
 * 简单的数据库连接(带有超时)
 * CountDownLatch(栅栏)
 * @author mgh_2
 *
 */

import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class C12_DBPool {

	private static LinkedList<SqlConnect> pool;
	private static final int POOL_SIZE = 4;
	private static final long EXPIRE_TIME = 500;
	private static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;
	private static CountDownLatch latch;

	public C12_DBPool(int length) {
		pool = new LinkedList<SqlConnect>();
		latch = new CountDownLatch(length);
		for (int i = 0; i < POOL_SIZE; i++) {
			pool.addLast(new SqlConnect());
		}
	}

	private SqlConnect fetchConn(long expireTime) throws InterruptedException {
		SqlConnect conn = null;
		long timeout = TimeUnit.MILLISECONDS.convert(expireTime, TIME_UNIT);
		long overTime = System.currentTimeMillis() + timeout;
		synchronized (pool) {
			if (timeout <= 0) {
				while ((conn = pool.pollFirst()) == null) {
					pool.wait();
				}
			} else {
				while ((conn = pool.pollFirst()) == null && timeout > 0) {
					pool.wait(timeout);
					timeout = overTime - System.currentTimeMillis();
				}
			}

		}
		if (conn != null) {
			System.out.println(Thread.currentThread().getName() + " get conn");
		}
		return conn;
	}

	private void releaseConn(SqlConnect conn) {
		if (conn == null)
			return;
		synchronized (pool) {
			pool.addLast(conn);
			System.out.println(Thread.currentThread().getName() + " release conn");
			pool.notifyAll();
		}
	}

	private static class Conn implements Runnable {
		C12_DBPool dbPool;
		AtomicInteger get;
		AtomicInteger getnot;

		public Conn(C12_DBPool dbPool, AtomicInteger get, AtomicInteger getnot) {
			this.dbPool = dbPool;
			this.get = get;
			this.getnot = getnot;
		}

		@Override
		public void run() {
			try {
				SqlConnect conn = dbPool.fetchConn(EXPIRE_TIME);
				if (conn == null) {
					getnot.incrementAndGet();
					System.out.println(Thread.currentThread().getName() + " do not get conn");
				} else {
					try {
						Thread.sleep(100);
					} finally {
						get.incrementAndGet();
						dbPool.releaseConn(conn);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			latch.countDown();
		}
	}

	private class SqlConnect {
	}

	public static void main(String[] args) throws InterruptedException {
		int length = 30;
		C12_DBPool dbPool = new C12_DBPool(length);
		AtomicInteger get = new AtomicInteger();
		AtomicInteger getnot = new AtomicInteger();
		Conn conn = new Conn(dbPool, get, getnot);
		for (int i = 0; i < length; i++) {
			new Thread(conn).start();
		}
		latch.await();
		System.out.println("get :"+get.get()+"/getnot:"+getnot.get());
	}
}

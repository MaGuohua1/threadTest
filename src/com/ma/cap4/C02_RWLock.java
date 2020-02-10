package com.ma.cap4;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/**
 * 在读多写少的情况下，使用ReadWriteLock与synchronized相比，可以大幅度的提高速度
 * @author mgh_2
 *
 */
public class C02_RWLock {
	static final int readWriteRatio = 10;//读写线程的比例
	static final int minThreadCount = 3;//最少线程数

	private static class GetThread implements Runnable {
		private GoodsService service;

		public GetThread(GoodsService service) {
			this.service = service;
		}

		@Override
		public void run() {
			long start = System.currentTimeMillis();
			for (int i = 0; i < 100; i++) {
				try {
					service.getNum();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(
					Thread.currentThread().getName() + " read timeout is " + (System.currentTimeMillis() - start));
		}
	}

	private static class SetThread implements Runnable {
		private GoodsService service;

		public SetThread(GoodsService service) {
			this.service = service;
		}

		@Override
		public void run() {
			long start = System.currentTimeMillis();
			Random random = new Random();
			for (int i = 0; i < 10; i++) {
				try {
					Thread.sleep(50);
					service.setNum(random.nextInt(10));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(
					Thread.currentThread().getName() + " write timeout is " + (System.currentTimeMillis() - start));
		}
	}

	public static void main(String[] args) throws InterruptedException {
		GoodsInfo goodsInfo = new GoodsInfo("cup", 100000, 10000);
//		GoodsService goods = new UseSyn(goodsInfo);
		GoodsService goods = new UseRWLock(goodsInfo);
		SetThread setThread = new SetThread(goods);
		GetThread getThread = new GetThread(goods);
		for (int i = 0; i < minThreadCount; i++) {
			Thread set = new Thread(setThread);
			for (int j = 0; j < readWriteRatio; j++) {
				Thread get = new Thread(getThread);
				get.start();
			}
			Thread.sleep(100);
			set.start();
		}
	}
}

interface GoodsService {

	GoodsInfo getNum() throws InterruptedException;

	void setNum(int number) throws InterruptedException;

}

class UseRWLock implements GoodsService {
	private GoodsInfo goodsInfo;
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	private Lock read = lock.readLock();
	private Lock write = lock.writeLock();

	public UseRWLock(GoodsInfo goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	@Override
	public GoodsInfo getNum() throws InterruptedException {
		try {
			read.lock();
			Thread.sleep(5);
			return goodsInfo;
		} finally {
			read.unlock();
		}
	}

	@Override
	public void setNum(int number) throws InterruptedException {
		try {
			write.lock();
			Thread.sleep(5);
			goodsInfo.changeNumber(number);
		} finally {
			write.unlock();
		}
	}
}

class UseSyn implements GoodsService {
	private GoodsInfo goodsInfo;

	public UseSyn(GoodsInfo goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	@Override
	public synchronized GoodsInfo getNum() throws InterruptedException {
		Thread.sleep(5);
		return goodsInfo;
	}

	@Override
	public synchronized void setNum(int number) throws InterruptedException {
		Thread.sleep(5);
		goodsInfo.changeNumber(number);
	}
}

class GoodsInfo {
	private String name;
	private int money;
	private int number;

	public GoodsInfo(String name, int money, int number) {
		this.name = name;
		this.money = money;
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public int getMoney() {
		return money;
	}

	public int getNumber() {
		return number;
	}

	public void changeNumber(int number) {
		this.money += number * 25;
		this.number -= number;
	}

}
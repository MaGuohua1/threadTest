package com.ma.cap7;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 动态死锁(账户交易死锁问题) 和两种解决死锁的办法
 * 
 * @author mgh_2
 *
 */
public class C02_DynamicDeadLock {
	private Object lock = new Object();

	// 引发死锁
	private void transfer(Account from, Account to, int money) {
		synchronized (from) {
			System.out.println("Lock the " + from.getName() + " Account.");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (to) {
				System.out.println("Lock the " + to.getName() + " Account.");
				from.flyMoney(money);
				to.addMoney(money);
				System.out.println(from.getName() + " to " + to.getName() + " transfer " + money + " Success");
			}
		}
	}

	// 解决死锁问题(保证锁 的一致性)
	private void transfer2(Account from, Account to, int money) {
		int fromId = System.identityHashCode(from);
		int toId = System.identityHashCode(to);
		try {
			if (fromId < toId) {
				transfer(from, to, money);
			} else if (toId < fromId) {
				synchronized (to) {
					System.out.println("Lock the " + to.getName() + " Account.");
					Thread.sleep(1000);
					synchronized (from) {
						System.out.println("Lock the " + from.getName() + " Account.");
						from.flyMoney(money);
						to.addMoney(money);
						System.out.println(from.getName() + " to " + to.getName() + " transfer " + money + " Success");
					}
				}
			} else {// 解决hash冲突
				synchronized (lock) {
					System.out.println("get the Lock");
					transfer(from, to, money);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		latch.countDown();
	}

	// 解决死锁
	private void transfer3(Account from, Account to, int money) {
		Random random = new Random();
		while (true) {
			if (from.getLock().tryLock()) {
				System.out.println("Lock the " + from.getName() + " Account.");
				try {
					if (to.getLock().tryLock()) {
						System.out.println("Lock the " + to.getName() + " Account.");
						try {
							from.flyMoney(money);
							to.addMoney(money);
							System.out.println(
									from.getName() + " to " + to.getName() + " transfer " + money + " Success");
							break;
						} finally {
							to.getLock().unlock();
						}
					}
				} finally {
					from.getLock().unlock();
				}
			}
			try {// 使用随机数解决活锁问题
				Thread.sleep(random.nextInt(10));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		latch.countDown();
	}

	private CountDownLatch latch = new CountDownLatch(4);

	public static void main(String[] args) throws InterruptedException {
		Account Lucy = new Account("Lucy", 100);
		Account Lily = new Account("Lily", 200);
		C02_DynamicDeadLock deadLock = new C02_DynamicDeadLock();

		new Thread(() -> deadLock.transfer2(Lucy, Lily, 50)).start();
		new Thread(() -> deadLock.transfer2(Lily, Lucy, 30)).start();
		new Thread(() -> deadLock.transfer3(Lucy, Lily, 50)).start();
		new Thread(() -> deadLock.transfer3(Lily, Lucy, 20)).start();
		deadLock.latch.await();
		System.out.println(Lucy.getMoney());
		System.out.println(Lily.getMoney());
	}
}

/**
 * 银行账户
 */
class Account {
	private String name;
	private double money;
	private Lock lock;

	public Account(String name, double money) {
		this.name = name;
		this.money = money;
		this.lock = new ReentrantLock();
	}

	public Lock getLock() {
		return lock;
	}

	public String getName() {
		return name;
	}

	public double getMoney() {
		return money;
	}

	public void addMoney(double money) {
		this.money += money;
	}

	public void flyMoney(double money) {
		this.money -= money;
	}
}
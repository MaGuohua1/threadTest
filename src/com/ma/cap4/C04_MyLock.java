package com.ma.cap4;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
/**
 * 实现自己的锁
 * @author mgh_2
 *
 */
public class C04_MyLock implements Lock {
	
	private final Sync sync = new Sync();

	private class Sync extends AbstractQueuedSynchronizer {

		private static final long serialVersionUID = -584597949414666903L;

		@Override
		protected boolean tryAcquire(int arg) {
			boolean flag = false;
			if (flag = compareAndSetState(0, arg)) {
				setExclusiveOwnerThread(Thread.currentThread());
			}
			return flag;
		}

		@Override
		protected boolean tryRelease(int arg) {
			if (getState() == 0) {
				throw new UnsupportedOperationException();
			}
			setState(arg);
			setExclusiveOwnerThread(null);
			return true;
		}

		@Override
		protected boolean isHeldExclusively() {//是否独占
			return getState() == 1;
		}
		
		Condition newCondition() {
			return new ConditionObject();
		}
		
	}
	
	@Override
	public void lock() {
		sync.acquire(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		sync.acquireInterruptibly(1);
	}

	@Override
	public boolean tryLock() {
		return sync.tryAcquire(1);
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return sync.tryAcquireNanos(1, unit.toNanos(time));
	}

	@Override
	public void unlock() {
		sync.release(0);
	}

	@Override
	public Condition newCondition() {
		return sync.newCondition();
	}

}

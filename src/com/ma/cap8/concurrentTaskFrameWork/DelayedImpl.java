package com.ma.cap8.concurrentTaskFrameWork;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedImpl<T> implements Delayed {

	private T data;
	private long activeTime;// 到期时间（纳秒）
	private TimeUnit unit;

	public DelayedImpl(T data, long milli) {
		this(data, milli, TimeUnit.MILLISECONDS);
	}

	public DelayedImpl(T data, long time, TimeUnit unit) {
		this.data = data;
		this.activeTime = unit.toNanos(time) + System.nanoTime();
		this.unit = unit;
	}

	public T getData() {
		return data;
	}

	public long getActiveTime() {
		return activeTime;
	}

	@Override
	public int compareTo(Delayed o) {
		long d = getDelay(unit) - o.getDelay(unit);
		return d == 0 ? 0 : d < 0 ? -1 : 1;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(activeTime - System.nanoTime(), TimeUnit.NANOSECONDS);
	}

}

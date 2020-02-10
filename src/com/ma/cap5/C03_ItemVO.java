package com.ma.cap5;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 存放到队列的元素（延时到期）
 * 
 * @author mgh_2
 *
 * @param <T>
 */
public class C03_ItemVO<T> implements Delayed {

	private T data;
	private long activeTime;// 到期时间（纳秒）
	private TimeUnit unit;// 时间单位

	public C03_ItemVO(T data, long activeTime) {// 默认为毫秒
		this(data, activeTime, TimeUnit.MILLISECONDS);
	}

	// activeTime是一个过期时长
	public C03_ItemVO(T data, long activeTime, TimeUnit unit) {// activeTime是毫秒
		this.activeTime = TimeUnit.NANOSECONDS.convert(activeTime, unit) + System.nanoTime();
		this.data = data;
		this.unit = unit;
	}

	public T getData() {
		return data;
	}

	public long getActiveTime() {
		return activeTime;
	}

	@Override
	public int compareTo(Delayed o) {// 按照剩余时间排序
		long d = getDelay(unit) - o.getDelay(unit);
		return d == 0 ? 0 : d < 0 ? -1 : 1;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(activeTime - System.nanoTime(), TimeUnit.NANOSECONDS);
	}

}

package com.ma.cap3;

import java.util.concurrent.atomic.AtomicInteger;

public class HalfAtomicInt {

	private AtomicInteger integer = new AtomicInteger();
	
	public int increamentAndGet() {
		while (true) {
			int i = integer.get();
			if (integer.compareAndSet(i, i++)) {
				break;
			}
		}
		return integer.get();
	}
	
	public void get() {
		integer.get();
	}
}

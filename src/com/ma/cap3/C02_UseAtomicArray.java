package com.ma.cap3;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class C02_UseAtomicArray {

	public static void main(String[] args) {
		int[] arr = { 1, 2 };
		AtomicIntegerArray array = new AtomicIntegerArray(arr);

		System.out.println(array.getAndAdd(0, 3));
		System.out.println(array.getAndSet(1, 4));

		System.out.println(array.toString());
	}
}

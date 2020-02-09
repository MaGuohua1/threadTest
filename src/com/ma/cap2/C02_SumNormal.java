package com.ma.cap2;

public class C02_SumNormal {

	public static void main(String[] args) throws InterruptedException {
		int[] arr = C01_SumForkJoin.initArray();
		int count = 0;
		long millis = System.currentTimeMillis();
		for (int i = 0; i < arr.length; i++) {
			Thread.sleep(1);
			count += arr[i];
		}
		System.out.println(count + " : " + (System.currentTimeMillis() - millis));
	}
}

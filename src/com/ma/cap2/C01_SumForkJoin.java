package com.ma.cap2;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class C01_SumForkJoin {
	private static final int LENGTH = 1000;

	private static class SumTask extends RecursiveTask<Integer> {

		private static final long serialVersionUID = -8254303854680315442L;
		private static final int THRESHOLD = LENGTH / (Runtime.getRuntime().availableProcessors());
		private int[] arr;
		private int start;
		private int end;

		public SumTask(int[] arr, int start, int end) {
			this.arr = arr;
			this.start = start;
			this.end = end;
		}

		@Override
		protected Integer compute() {
			if (arr == null) {
				return 0;
			}
			if (end - start + 1 < THRESHOLD) {
				int count = 0;
				for (int i = start; i <= end; i++) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					count += arr[i];
				}
				return count;
			} else {
				int mid = (start + end) / 2;
				SumTask left = new SumTask(arr, start, mid);
				SumTask right = new SumTask(arr, mid + 1, end);
				invokeAll(left, right);
				return left.join() + right.join();
			}
		}
	}

	public static void main(String[] args) {
		int[] arr = initArray();
		ForkJoinPool pool = new ForkJoinPool();
		SumTask task = new SumTask(arr, 0, arr.length - 1);
		long millis = System.currentTimeMillis();
		Integer invoke = pool.invoke(task);
		System.out.println(invoke);
		System.out.println(System.currentTimeMillis() - millis);
	}

	static int[] initArray() {
		int[] arr = new int[LENGTH];
		Random random = new Random();
		for (int i = 0; i < arr.length; i++) {
			arr[i] = random.nextInt(100);
		}
		return arr;
	}
}
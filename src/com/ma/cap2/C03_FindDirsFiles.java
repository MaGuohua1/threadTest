package com.ma.cap2;

import java.io.File;
import java.util.LinkedList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class C03_FindDirsFiles extends RecursiveAction {
	private static final long serialVersionUID = 5161829475722460548L;
	private File root;

	public C03_FindDirsFiles(File root) {
		this.root = root;
	}

	@Override
	protected void compute() {
		if (root == null) {
			return;
		}
		if (!root.isDirectory()) {
			if (root.getAbsolutePath().endsWith(".java")) {
				System.out.println(root.getName());
			}
		} else {
			File[] files = root.listFiles();
			LinkedList<C03_FindDirsFiles> list = new LinkedList<>();
			for (int i = 0; i < files.length; i++) {
				list.addLast(new C03_FindDirsFiles(files[i]));
			}
			if (!list.isEmpty()) {
				for (C03_FindDirsFiles task : invokeAll(list)) {
					task.join();
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ForkJoinPool pool = new ForkJoinPool();
		C03_FindDirsFiles files = new C03_FindDirsFiles(new File("E:\\works\\sts\\threadTest"));
		pool.execute(files);// 异步执行
		Thread.sleep(5000);
		System.out.println("sleep end");
		files.join();
	}
}

package com.ma.cap7;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class C03_Singleton {

	public static void main(String[] args) {
		FutureTask<Singleton> task1 = new FutureTask<Singleton>(() -> Singleton.getInstance());
		FutureTask<Singleton> task2 = new FutureTask<Singleton>(() -> Singleton.getInstance());
		new Thread(task1).start();
		new Thread(task2).start();
		try {
			System.out.println(task1.get() == task2.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		FutureTask<Singleton1> task11 = new FutureTask<Singleton1>(() -> Singleton1.getInstance());
		FutureTask<Singleton1> task22 = new FutureTask<Singleton1>(() -> Singleton1.getInstance());
		new Thread(task11).start();
		new Thread(task22).start();
		try {
			System.out.println(task11.get() == task22.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		FutureTask<Singleton2> task111 = new FutureTask<Singleton2>(() -> Singleton2.getInstance());
		FutureTask<Singleton2> task222 = new FutureTask<Singleton2>(() -> Singleton2.getInstance());
		new Thread(task111).start();
		new Thread(task222).start();
		try {
			System.out.println(task111.get() == task222.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		FutureTask<Singleton3> task1111 = new FutureTask<Singleton3>(() -> Singleton3.getInstance());
		FutureTask<Singleton3> task2222 = new FutureTask<Singleton3>(() -> Singleton3.getInstance());
		new Thread(task1111).start();
		new Thread(task2222).start();
		try {
			System.out.println(task1111.get() == task2222.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}

//饿汉式（线程安全）
class Singleton {
	private static Singleton singleton = new Singleton();

	private Singleton() {
	}

	public static Singleton getInstance() {
		return singleton;
	}
}

//懒汉式（线程安全）
class Singleton1 {
	private Singleton1() {
	}

	private static class Instance {
		private static Singleton1 singleton = new Singleton1();
	}

	public static Singleton1 getInstance() {
		return Instance.singleton;
	}
}

//懒汉式
class Singleton2 {
	private static Singleton2 singleton;

	private Singleton2() {
	}

	public static Singleton2 getInstance() {
		if (singleton == null) {
			singleton = new Singleton2();
		}
		return singleton;
	}
}

//懒汉式(双重检查)
class Singleton3 {
	private static Singleton3 singleton;

	private Singleton3() {
	}

	public static Singleton3 getInstance() {
		if (singleton == null) {
			synchronized (Singleton3.class) {
				if (singleton == null) {
					singleton = new Singleton3();
				}
			}
		}
		return singleton;
	}
}

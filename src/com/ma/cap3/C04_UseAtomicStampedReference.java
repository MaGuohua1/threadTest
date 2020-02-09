package com.ma.cap3;

import java.util.concurrent.atomic.AtomicStampedReference;
/**
 * AtomicStampedReference带版本戳的原子类
 * @author mgh_2
 *
 */
public class C04_UseAtomicStampedReference {

	public static void main(String[] args) throws InterruptedException {
		AtomicStampedReference<String> reference = new AtomicStampedReference<String>("init", 0);
		int oldStamp = reference.getStamp();
		String oldStr = reference.getReference();
		System.out.println(oldStamp + " : " + oldStr);

		Thread right = new Thread(() -> {
			System.out.println(oldStr + "/" + oldStamp + " need change, but now is " + reference.getReference() + "/"
					+ reference.getStamp());
			System.out.println("chenged is " + reference.compareAndSet(oldStr, "first", oldStamp, oldStamp + 1));
		}, "right");
		right.start();

		Thread error = new Thread(() -> {
			System.out.println(oldStr + "/" + oldStamp + " need change, but now is " + reference.getReference() + "/"
					+ reference.getStamp());
			System.out.println("chenged is " + reference.compareAndSet(oldStr, "second", oldStamp, oldStamp + 1));
		}, "error");
		error.start();

	}

}

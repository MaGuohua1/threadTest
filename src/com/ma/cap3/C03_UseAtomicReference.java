package com.ma.cap3;

import java.util.concurrent.atomic.AtomicReference;

public class C03_UseAtomicReference {

	public static void main(String[] args) {
		C03_UseAtomicReference useAtomicReference = new C03_UseAtomicReference();
		User user = useAtomicReference.new User("james", 66);
		User user1 = useAtomicReference.new User("lily", 18);
		AtomicReference<User> reference = new AtomicReference<C03_UseAtomicReference.User>(user);
		reference.compareAndSet(user, user1);
		System.out.println(reference.get());
		user.age=25;
		System.out.println(reference.compareAndSet(user, user));
		System.out.println(user);
		System.out.println(user1);
	}

	private class User {
		private String name;
		private Integer age;

		public User(String name, Integer age) {
			this.name = name;
			this.age = age;
		}

		@Override
		public String toString() {
			return "User [name=" + name + ", age=" + age + "]";
		}

	}
}

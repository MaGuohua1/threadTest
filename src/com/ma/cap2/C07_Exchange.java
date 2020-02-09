package com.ma.cap2;

import java.util.concurrent.Exchanger;

public class C07_Exchange {

	private class Change implements Runnable {
		private Exchanger<String> exchanger;
		private String str;

		public Change(Exchanger<String> exchanger, String str) {
			this.exchanger = exchanger;
			this.str = str;
		}

		@Override
		public void run() {
			try {
				String exchange = exchanger.exchange(str);
				System.out.println(Thread.currentThread().getName() + " change " + str + " to " + exchange);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Exchanger<String> exchanger = new Exchanger<String>();
		C07_Exchange c07_Exchange = new C07_Exchange();
		Change thread = c07_Exchange.new Change(exchanger, "11111");
		Change thread1 = c07_Exchange.new Change(exchanger, "22222");
		new Thread(thread).start();
		new Thread(thread1).start();
	}
}

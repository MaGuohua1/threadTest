package com.ma.cap5;

import java.util.concurrent.DelayQueue;

public class C03_TestItemVO {
	
	public static void main(String[] args) {
		C03_TestItemVO c03_TestItemVO = new C03_TestItemVO();
		c03_TestItemVO.test(c03_TestItemVO);
	}

	private void test(C03_TestItemVO c03_TestItemVO) {
		DelayQueue<C03_ItemVO<Order>> queue = new DelayQueue<C03_ItemVO<Order>>();
		PutOrder putOrder = c03_TestItemVO.new PutOrder(queue);
		FetchOrder fetchOrder = c03_TestItemVO.new FetchOrder(queue);
		
		new Thread(putOrder).start();
		new Thread(fetchOrder).start();
		
		for (int i = 0; i < 15; i++) {
			try {
				Thread.sleep(500);
				System.out.println(i*500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private class PutOrder implements Runnable {
		
		private DelayQueue<C03_ItemVO<Order>> queue;
		
		public PutOrder(DelayQueue<C03_ItemVO<Order>> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			Order order = new Order("11111",5000);
			C03_ItemVO<Order> itemVO = new C03_ItemVO<Order>(order, 5000);
			queue.offer(itemVO);
			System.out.println("订单5秒后到期"+order.getOrderNo());
			
			order = new Order("22222",8000);
			itemVO = new C03_ItemVO<Order>(order, 8000);
			queue.offer(itemVO);
			System.out.println("订单8秒后到期"+order.getOrderNo());
		}
		
	}
	
	private class FetchOrder implements Runnable {
		
		private DelayQueue<C03_ItemVO<Order>> queue;
		private Order order;
		public FetchOrder(DelayQueue<C03_ItemVO<Order>> queue) {
			this.queue = queue;
		}
		
		@Override
		public void run() {
			while (true) {
				try {
					C03_ItemVO<Order> itemVO = queue.take();
					order = itemVO.getData();
					System.out.println("得到订单"+order.getOrderNo());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private class Order {
		private final String orderNo;
		private final double orderNoney;
		
		public Order(String orderNo, double orderNoney) {
			this.orderNo = orderNo;
			this.orderNoney = orderNoney;
		}
		
		public String getOrderNo() {
			return orderNo;
		}
		
		@SuppressWarnings("unused")
		public double getOrderNoney() {
			return orderNoney;
		}
	}
}
package com.ma.cap5;

/**
 * 位运算演示
 * 
 * @author mgh_2
 *
 */
public class C01_IntToBinary {

	public static void main(String[] args) {
		int a = 4;
		int b = 6;
		int c = -6;
		System.out.println("the " + a + " Binary is " + Integer.toBinaryString(a));
		System.out.println("the " + b + " Binary is " + Integer.toBinaryString(b));
		System.out.println("the " + c + " Binary is " + Integer.toBinaryString(c));
		// 位& (1&1=1,1&0=0,0&0=0)
		System.out.println("the " + a + "&" + b + " Binary is " + Integer.toBinaryString(a & b));
		// 位| (1|1=1,1|0=1,0|0=0)
		System.out.println("the " + a + "|" + b + " Binary is " + Integer.toBinaryString(a | b));
		// 位~ (~1=0,~0=1)
		System.out.println("the ~" + a + " Binary is " + Integer.toBinaryString(~a));
		// 位^ (1^1=0,0^0=0,1^0=1)
		System.out.println("the " + a + "^" + b + " Binary is " + Integer.toBinaryString(a ^ b));
		// <<有符号左移
		System.out.println("the " + a + "<<1 Binary is " + Integer.toBinaryString(a << 1));
		// >>有符号右移
		System.out.println("the " + a + ">>1 Binary is " + Integer.toBinaryString(a >> 1));
		System.out.println("the " + c + ">>1 is " + (c >> 1));
		System.out.println("the " + c + ">>1 Binary is " + Integer.toBinaryString(c >> 1));
		// >>>无符号右移
		System.out.println("the " + b + ">>>1 is " + (b >>> 1));
		System.out.println("the " + b + ">>>1 Binary is " + Integer.toBinaryString(b >>> 1));
		System.out.println("the " + c + ">>>1 is " + (c >>> 1));
		System.out.println("the " + c + ">>>1 Binary is " + Integer.toBinaryString(c >>> 1));

		// 取模操作 a % b == a & (2^n-1)
		a = 17;
		b = 8;
		System.out.println("the " + a + " Binary is " + Integer.toBinaryString(a));
		System.out.println("the " + b + " Binary is " + Integer.toBinaryString(b));
		System.out.println("the " + (b - 1) + " Binary is " + Integer.toBinaryString(b - 1));
		System.out.println("the " + a + "%" + b + " is " + (a % b));
		System.out.println("the " + a + "&" + (b - 1) + " Binary is " + (a & (b - 1)));

	}
}

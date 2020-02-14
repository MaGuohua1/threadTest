package com.ma.cap10;

import java.util.HashMap;
import java.util.Map;

public class test {
	public static void main(String[] args) {
		Map<Integer, String> map =new HashMap<Integer, String>();
		System.out.println(map.putIfAbsent(1, "11"));
		System.out.println(map.putIfAbsent(1, "22"));
		System.out.println(map.get(1));
	}
}

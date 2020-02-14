package com.ma.cap9.assist;
/**
 * @description 用sleep模拟实际的业务操作
 * @author mgh_2
 *
 */
public class SL_Busi {

	public static void busness(int sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

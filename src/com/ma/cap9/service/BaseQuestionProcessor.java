package com.ma.cap9.service;

import java.util.Random;

import com.ma.cap9.assist.SL_Busi;

public class BaseQuestionProcessor {

	public static String makeQuestion(Integer id, String detail) {
		Random r = new Random();
		SL_Busi.busness(450 + r.nextInt(100));
		return "CompleteQuestion[id=" + id + " content=" + detail + "]";
	}
}

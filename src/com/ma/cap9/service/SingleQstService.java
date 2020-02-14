package com.ma.cap9.service;

import com.ma.cap9.assist.SL_QuestionBank;

public class SingleQstService {

	public static String makeQuestion(Integer id) {
		return BaseQuestionProcessor.makeQuestion(id, SL_QuestionBank.getQuestion(id).getDetail());
	}

}

package com.ma.cap9.vo;

import java.util.List;

public class SrcDocVo {

	private final String docName;
	private List<Integer> questionList;

	public SrcDocVo(String docName, List<Integer> questionList) {
		this.docName = docName;
		this.questionList = questionList;
	}

	public String getDocName() {
		return docName;
	}

	public List<Integer> getQuestionList() {
		return questionList;
	}
}

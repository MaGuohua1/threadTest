package com.ma.cap9.vo;

public class QuestionInDBVo {
	private final int id;
	private final String detail;
	private final String sha;

	public QuestionInDBVo(int id, String detail, String sha) {
		this.id = id;
		this.detail = detail;
		this.sha = sha;
	}

	public String getSha() {
		return sha;
	}

	public int getId() {
		return id;
	}

	public String getDetail() {
		return detail;
	}
}

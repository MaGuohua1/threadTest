package com.ma.cap9.vo;

public class QuestionCacheVo {

	private final String sha;
	private final String detail;

	public QuestionCacheVo(String sha, String detail) {
		this.sha = sha;
		this.detail = detail;
	}

	public String getSha() {
		return sha;
	}

	public String getDetail() {
		return detail;
	}
}

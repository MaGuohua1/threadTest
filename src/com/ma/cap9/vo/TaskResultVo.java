package com.ma.cap9.vo;

import java.util.concurrent.Future;

public class TaskResultVo {

	private final String detail;
	private final Future<QuestionCacheVo> future;

	public TaskResultVo(String detail) {
		this.detail = detail;
		this.future = null;
	}

	public TaskResultVo(Future<QuestionCacheVo> future) {
		this.detail = null;
		this.future = future;
	}

	public String getDetail() {
		return detail;
	}

	public Future<QuestionCacheVo> getFuture() {
		return future;
	}

}

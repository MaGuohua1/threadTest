package com.ma.cap9.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import com.ma.cap9.assist.Consts;
import com.ma.cap9.assist.SL_QuestionBank;
import com.ma.cap9.vo.QuestionCacheVo;
import com.ma.cap9.vo.QuestionInDBVo;
import com.ma.cap9.vo.TaskResultVo;

public class ParallelQstService {

	// 题目缓存
	private static ConcurrentHashMap<Integer, QuestionCacheVo> questionCache = new ConcurrentHashMap<>();
	// 正在缓存的题目
	private static ConcurrentHashMap<Integer, Future<QuestionCacheVo>> processCache = new ConcurrentHashMap<>();

	private static ExecutorService pool = Executors.newFixedThreadPool(Consts.CORE_COUNT * 2);

	private static class QuestionTask implements Callable<QuestionCacheVo> {
		private QuestionInDBVo question;
		private Integer id;

		public QuestionTask(Integer id, QuestionInDBVo question) {
			this.id = id;
			this.question = question;
		}

		@Override
		public QuestionCacheVo call() throws Exception {
			try {
				String detail = BaseQuestionProcessor.makeQuestion(id, SL_QuestionBank.getQuestion(id).getDetail());
				String sha = question.getSha();
				QuestionCacheVo cache = new QuestionCacheVo(sha, detail);
				questionCache.put(id, cache);
				return cache;
			} finally {
				processCache.remove(id);
			}
		}
	}

	public static TaskResultVo makeQuestion(Integer id) {
		QuestionCacheVo cacheVo = questionCache.get(id);
		if (cacheVo == null) {
			System.out.println("题目[" + id + "]在缓存中不存在,直接获取");
			return new TaskResultVo(getQstFuture(id));
		} else {
			if (SL_QuestionBank.getSha(id).equals(cacheVo.getSha())) {
				System.out.println("题目[" + id + "]在缓存中已经存在,从缓存中获取");
				return new TaskResultVo(cacheVo.getDetail());
			} else {
				System.out.println("题目[" + id + "]在缓存中已经过时,更新缓存");
				return new TaskResultVo(getQstFuture(id));
			}
		}
	}

	private static Future<QuestionCacheVo> getQstFuture(Integer id) {
		Future<QuestionCacheVo> future = processCache.get(id);
		try {
			if (future == null) {
				QuestionInDBVo question = SL_QuestionBank.getQuestion(id);
				FutureTask<QuestionCacheVo> fq = new FutureTask<>(new QuestionTask(id, question));
				future = processCache.putIfAbsent(id, fq);
				if (future == null) {
					pool.execute(fq);
					future = fq;
					System.out.println("成功启动了题目[" + id + "]的计算任务,请等待完成>>>>>>>");
				} else {
					System.out.println("<<<<<<<<<<<<有其他线程刚刚启动了题目[" + id + "]的计算任务,本任务无需开启");
				}
			} else {
				System.out.println("题目[" + id + "]已经存在计算任务,无需重新生成");
			}
		} catch (Exception e) {
			processCache.remove(id);
			e.printStackTrace();
		}
		return future;
	}

}

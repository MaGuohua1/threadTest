package com.ma.cap9.assist;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.ma.cap9.vo.QuestionInDBVo;

/**
 * 
 * @author mgh_2
 *
 * @desription 模拟存储在数据库中的题库数据
 */
public class SL_QuestionBank {

	// 存储题库数据
	private static ConcurrentHashMap<Integer, QuestionInDBVo> questionBankMap = new ConcurrentHashMap<>();
	// 定时任务池,负责定时更新题库数据
	private static ScheduledExecutorService updateQuestionBank = new ScheduledThreadPoolExecutor(1);

	// 初始化数据库
	public static void initBank() {
		for (int i = 0; i < Consts.SIZE_OF_QUESTION_BANK; i++) {
			String questionContest = makeQuestionDetail(Consts.QUESTION_LENGTH);
			questionBankMap.put(i, new QuestionInDBVo(i, questionContest, EncryptUtils.EncryptBySHA1(questionContest)));
		}
		updateQuestionTimer();// 启动定期更新题库数据任务
	}

	private static void updateQuestionTimer() {
		updateQuestionBank.scheduleAtFixedRate(new UpdateBank(), 15, 5, TimeUnit.SECONDS);

	}

	// 生成随机字符串,代表题目的实际内容, 题目的长度length
	private static String makeQuestionDetail(int length) {
		String base = "abcdefghijklmnopqresuvwxyz0123456789";
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	// 更新题库的定时任务
	private static class UpdateBank implements Runnable {
		@Override
		public void run() {
			Random random = new Random();
			int questionId = random.nextInt(Consts.SIZE_OF_QUESTION_BANK);
			String questionContest = makeQuestionDetail(Consts.QUESTION_LENGTH);
			questionBankMap.put(questionId,
					new QuestionInDBVo(questionId, questionContest, EncryptUtils.EncryptBySHA1(questionContest)));
			System.out.println("题目【" + questionId + "】被更新！！！");
		}
	}

	public static QuestionInDBVo getQuestion(int i) {
		SL_Busi.busness(20);
		return questionBankMap.get(i);
	}
	public static String getSha(int i) {
		SL_Busi.busness(10);
		return questionBankMap.get(i).getSha();
	}

}

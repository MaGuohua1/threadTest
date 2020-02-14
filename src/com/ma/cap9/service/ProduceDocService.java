package com.ma.cap9.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import com.ma.cap9.assist.SL_Busi;
import com.ma.cap9.vo.SrcDocVo;
import com.ma.cap9.vo.TaskResultVo;

/**
 * 
 * @author mgh_2
 * @desription 处理文档的服务，包括文档中题目的处理和文档生成后的上传
 */
public class ProduceDocService {

	public static String makeDoc(SrcDocVo doc) {
		System.out.println("开始处理文档" + doc.getDocName());
		StringBuffer sb = new StringBuffer();
		for (Integer id : doc.getQuestionList()) {
			sb.append(SingleQstService.makeQuestion(id));
		}
		return "complete_" + System.currentTimeMillis() + "_" + doc.getDocName() + ".pdf";
	}

	public static String uploadDoc(String localName) {
		Random r = new Random();
		SL_Busi.busness(9000 + r.nextInt(400));
		return "http://www.xxxx.com/file/upload/" + localName;
	}

	public static String makeDocAsync(SrcDocVo doc) {
		System.out.println("开始处理文档" + doc.getDocName());
		Map<Integer, TaskResultVo> map = new LinkedHashMap<Integer, TaskResultVo>();
		for (Integer id : doc.getQuestionList()) {
			map.put(id, ParallelQstService.makeQuestion(id));
		}
		StringBuffer sb = new StringBuffer();
		for (Integer id : doc.getQuestionList()) {
			try {
				TaskResultVo taskResult = map.get(id);
				sb.append(taskResult.getDetail() == null ? taskResult.getFuture().get().getDetail()
						: taskResult.getDetail());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		return "complete_" + System.currentTimeMillis() + "_" + doc.getDocName() + ".pdf";
	}

}

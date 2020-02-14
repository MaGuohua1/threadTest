package com.ma.cap9;

import java.util.List;

import com.ma.cap9.assist.CreatePendingDocs;
import com.ma.cap9.assist.SL_QuestionBank;
import com.ma.cap9.service.ProduceDocService;
import com.ma.cap9.vo.SrcDocVo;

public class SingleWeb {
	public static void main(String[] args) {
		System.out.println("题库开始初始化………");
		SL_QuestionBank.initBank();
		System.out.println("题库初始化完成。");
		List<SrcDocVo> docList = CreatePendingDocs.makePendingDoc(2);
		long startTotal = System.currentTimeMillis();
		for (SrcDocVo doc : docList) {
			System.out.println();
			long start = System.currentTimeMillis();
			String localName = ProduceDocService.makeDoc(doc);
			System.out.println("文档" + localName + "生成耗时：" + (System.currentTimeMillis() - start) + " ms");
			start = System.currentTimeMillis();
			String remoteUrl = ProduceDocService.uploadDoc(localName);
			System.out.println("已上传至 [" + remoteUrl + "] 耗时：" + (System.currentTimeMillis() - start) + " ms");
		}
		System.out.println("------------共耗时："+(System.currentTimeMillis()-startTotal)+" ms");
	}

}

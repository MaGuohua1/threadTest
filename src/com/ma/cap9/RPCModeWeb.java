package com.ma.cap9;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;

import com.ma.cap9.assist.Consts;
import com.ma.cap9.assist.CreatePendingDocs;
import com.ma.cap9.assist.SL_QuestionBank;
import com.ma.cap9.service.ProduceDocService;
import com.ma.cap9.vo.SrcDocVo;

/**
 * 
 * @author mgh_2
 *
 * @desription rpc服务端，采用生产者消费者模式，生产者和消费者级联
 */
public class RPCModeWeb {
	
	// 生成文档
	private static Executor makeDocPool = Executors.newFixedThreadPool(Consts.CORE_COUNT * 2);
	// 上传文档
	private static Executor uploadDocPool = Executors.newFixedThreadPool(Consts.CORE_COUNT * 2);

	private static CompletionService<String> makeDocservice = new ExecutorCompletionService<String>(makeDocPool);
	private static CompletionService<String> uploadDocservice = new ExecutorCompletionService<String>(uploadDocPool);

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println("题库开始初始化………");
		SL_QuestionBank.initBank();
		System.out.println("题库初始化完成。");
		RPCModeWeb rpcModeWeb = new RPCModeWeb();
		rpcModeWeb.test();
	}

	private void test() throws InterruptedException, ExecutionException {
		List<SrcDocVo> docList = CreatePendingDocs.makePendingDoc(16);
		long startTotal = System.currentTimeMillis();
		for (SrcDocVo doc : docList) {
			makeDocservice.submit(new MakeDocTask(doc));
		}
		for (@SuppressWarnings("unused")
		SrcDocVo doc : docList) {
			String localName = makeDocservice.take().get();
			uploadDocservice.submit(new UploadDocTask(localName));
		}
		for (@SuppressWarnings("unused")
		SrcDocVo doc : docList) {
			uploadDocservice.take().get();
		}
		System.out.println("------------共耗时：" + (System.currentTimeMillis() - startTotal) + " ms");
	}

	private class MakeDocTask implements Callable<String> {
		private SrcDocVo doc;

		public MakeDocTask(SrcDocVo doc) {
			this.doc = doc;
		}

		@Override
		public String call() throws Exception {
			long start = System.currentTimeMillis();
//			String localName = ProduceDocService.makeDoc(doc);
			String localName = ProduceDocService.makeDocAsync(doc);
			System.out.println("文档" + localName + "生成耗时：" + (System.currentTimeMillis() - start) + " ms");
			return localName;
		}
	}

	private class UploadDocTask implements Callable<String> {
		private String localName;

		public UploadDocTask(String localName) {
			this.localName = localName;
		}

		@Override
		public String call() throws Exception {
			long start = System.currentTimeMillis();
			String remoteUrl = ProduceDocService.uploadDoc(localName);
			System.out.println("已上传至 [" + remoteUrl + "] 耗时：" + (System.currentTimeMillis() - start) + " ms");
			return remoteUrl;
		}
	}

}

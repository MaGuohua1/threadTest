package com.ma.cap9.assist;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.ma.cap9.vo.SrcDocVo;

public class CreatePendingDocs {

	public static List<SrcDocVo> makePendingDoc(int count) {
		Random r =new Random();
		List<SrcDocVo> list = new LinkedList<SrcDocVo>();
		for (int i = 0; i < count; i++) {
			List<Integer> questionList = new LinkedList<Integer>();
			for (int j = 0; j < Consts.QUESTION_COUNT_IN_DOC; j++) {
				questionList.add(r.nextInt(Consts.SIZE_OF_QUESTION_BANK));
			}
			SrcDocVo srcDocVo = new SrcDocVo("pending_"+i,questionList);
			list.add(srcDocVo);
		}
		return list;
	}

}

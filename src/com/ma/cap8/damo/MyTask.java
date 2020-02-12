package com.ma.cap8.damo;

import java.util.Random;

import com.ma.cap8.concurrentTaskFrameWork.ITaskProcessor;
import com.ma.cap8.concurrentTaskFrameWork.TaskResult;
import com.ma.cap8.concurrentTaskFrameWork.TaskResultType;

public class MyTask implements ITaskProcessor<Integer, Integer> {

	@Override
	public TaskResult<Integer> taskExecute(Integer task) {
		int random = new Random().nextInt(500);
		try {
			Thread.sleep(random);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		if (random <= 300) {
			return new TaskResult<Integer>(task.intValue() + random);
		} else if (random > 300 && random <= 400) {
			return new TaskResult<Integer>(TaskResultType.Failure, -1, "Failure");
		} else {
			try {
				throw new RuntimeException("Exception happened.");
			} catch (Exception e) {
				return new TaskResult<Integer>(TaskResultType.Exception, -1, "Exception" + e.getMessage());
			}
		}
	}

}

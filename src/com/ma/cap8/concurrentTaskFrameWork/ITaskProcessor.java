package com.ma.cap8.concurrentTaskFrameWork;

public interface ITaskProcessor<R, T> {

	TaskResult<R> taskExecute(T task);

}

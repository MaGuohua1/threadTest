package com.ma.cap8.concurrentTaskFrameWork;

public class TaskResult<R> {

	private final TaskResultType type;
	private final R returnValue;
	private final String reason;

	public TaskResult(R returnValue) {
		this(TaskResultType.Success, returnValue, "Success");
	}

	public TaskResult(TaskResultType type, R returnValue, String reason) {
		this.type = type;
		this.returnValue = returnValue;
		this.reason = reason;
	}

	public TaskResultType getType() {
		return type;
	}

	public R getReturnValue() {
		return returnValue;
	}

	public String getReason() {
		return reason;
	}

	@Override
	public String toString() {
		return "TaskResult [type=" + type + ", returnValue=" + returnValue + ", reason=" + reason + "]";
	}
}

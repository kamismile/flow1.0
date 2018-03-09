package com.shziyuan.support.schedule.service.history;

/**
 * 单一任务运行历史
 * @author james.hu
 *
 */
public class SingleHistory implements ScheduleHistory{

	protected long startTime;		// 启动时间
	protected long endTime;			// 结束时间
	protected boolean success;		// 是否成功
	protected Object result;		// 附加消息内容
	
	@Override
	public void runningLog(long startTime, long endTime, boolean success, Object result) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.success = success;
		this.result = result;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}

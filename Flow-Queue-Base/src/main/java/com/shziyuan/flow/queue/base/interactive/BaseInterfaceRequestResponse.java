package com.shziyuan.flow.queue.base.interactive;

import java.util.Map;

import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap;
import com.shziyuan.flow.global.executor.IInterfaceRequestResponse;

/**
 * 接口访问响应结构 基类
 * @author james.hu
 *
 */
public class BaseInterfaceRequestResponse implements IInterfaceRequestResponse{
	protected QueueOrderMQWrap queueSource;		// 源请求数据
	protected int interfaceStatusCode;			// 接口的状态码
	protected String submitRaw;					// 请求的源数据
	protected String resultRaw;					// 访问结果 源数据
	protected String resultCode;				// 访问结果 代码
	protected String resultMsg;					// 访问结果 消息说明
	protected Processing processing;			// 访问处理结果
	protected Map<String, Object> remark;		// 备用附加参数
	
	protected String passiveRedisKey;			// 仅当供应商是被动推送时使用,存入redis缓存数据的key
	
	
	public BaseInterfaceRequestResponse(QueueOrderMQWrap queueSource) {
		this.queueSource = queueSource;
	}
	
	public BaseInterfaceRequestResponse() {
	
	}
	
	protected void setResult(String source,String code,String msg) {
		this.resultRaw = source;
		this.resultCode = code;
		this.resultMsg = msg;
	}
	public void success(String source,String code,String msg) {
		this.processing = Processing.SUCCESS;
		setResult(source, code, msg);
	}
	
	public void faild(String source,String code,String msg) {
		this.processing = Processing.FAILD;
		setResult(source, code, msg);
	}
	
	public void success(String code,String msg) {
		this.processing = Processing.SUCCESS;
		setResultCode(code);
		setResultMsg(msg);
	}
	
	public void faild(String code,String msg) {
		this.processing = Processing.FAILD;
		setResultCode(code);
		setResultMsg(msg);
	}
	
	public void hold(String code,String msg) {
		this.processing = Processing.HOLD;
		setResultCode(code);
		setResultMsg(msg);
	}

	public void setQueueSource(QueueOrderMQWrap queueSource) {
		this.queueSource = queueSource;
	}
	
	public QueueOrderMQWrap getQueueSource() {
		return queueSource;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public void setProcessing(Processing processing) {
		this.processing = processing;
	}

	public void setRemark(Map<String, Object> remark) {
		this.remark = remark;
	}

	@Override
	public String getResultCode() {
		return resultCode;
	}

	@Override
	public String getResultMsg() {
		return resultMsg;
	}
	
	@Override
	public boolean isSuccess() {
		return processing.equals(Processing.SUCCESS);
	}

	@Override
	public boolean isFaild() {
		return processing.equals(Processing.FAILD);
	}

	@Override
	public boolean isHold() {
		return processing.equals(Processing.HOLD);
	}

	@Override
	public void setResultRaw(String raw) {
		this.resultRaw = raw;
	}

	@Override
	public Map<String, Object> getRemark() {
		return remark;
	}

	@Override
	public String getResultRaw() {
		return resultRaw;
	}

	public Processing getProcessing() {
		return processing;
	}

	public int getInterfaceStatusCode() {
		return interfaceStatusCode;
	}

	public void setInterfaceStatusCode(int interfaceStatusCode) {
		this.interfaceStatusCode = interfaceStatusCode;
	}

	public String getPassiveRedisKey() {
		return passiveRedisKey;
	}

	public void setPassiveRedisKey(String passiveRedisKey) {
		this.passiveRedisKey = passiveRedisKey;
	}

	public String getSubmitRaw() {
		return submitRaw;
	}

	public void setSubmitRaw(String submitRaw) {
		this.submitRaw = submitRaw;
	}

}

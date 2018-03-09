package com.shziyuan.flow.mq.stream.consumer;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.handler.annotation.Payload;

import com.shziyuan.flow.global.domain.stream.ConfigRefreshDomain;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.mq.stream.conf.RabbitConfiguration;


@RabbitListener(queues = "#{rabbitConfiguration.NOTI_CONFIG + rabbitConfiguration.notiQueueUUID}")
@ConditionalOnProperty(name="system.useRabbitNoti.config",havingValue="true")
public abstract class StreamConfigInputService {

	public abstract String showQueueName();
	
	private Map<String,Method> processMethodMap = new HashMap<>(6);
	
	@PostConstruct
	public void init() {
		processMethodMap.put(RabbitConfiguration.ROUTEKEY.NOTI_CONFIG_SUPPLIER.val, getMethod("doChangeSupplier"));
		processMethodMap.put(RabbitConfiguration.ROUTEKEY.NOTI_CONFIG_SUPPLIERCODE.val, getMethod("doChangeSuppliercode"));
		processMethodMap.put(RabbitConfiguration.ROUTEKEY.NOTI_CONFIG_DISTRIBUTOR.val, getMethod("doChangeDistributor"));
		processMethodMap.put(RabbitConfiguration.ROUTEKEY.NOTI_CONFIG_SKU.val, getMethod("doChangeSku"));
		processMethodMap.put(RabbitConfiguration.ROUTEKEY.NOTI_CONFIG_SUPPLIER_BIND.val, getMethod("doChangeBindSupplier"));
		processMethodMap.put(RabbitConfiguration.ROUTEKEY.NOTI_CONFIG_DISTRIBUTOR_BIND.val, getMethod("doChangeBindDistributor"));
	}
	
	protected abstract void doChangeSupplier(ConfigRefreshDomain domain);
	protected abstract void doChangeSuppliercode(ConfigRefreshDomain domain);
	protected abstract void doChangeDistributor(ConfigRefreshDomain domain);
	protected abstract void doChangeSku(ConfigRefreshDomain domain);
	protected abstract void doChangeBindSupplier(ConfigRefreshDomain domain);
	protected abstract void doChangeBindDistributor(ConfigRefreshDomain domain);
	
	@RabbitHandler
	public void process(@Payload ConfigRefreshDomain message,Message source) throws IOException {
		try {
			String key = source.getMessageProperties().getReceivedRoutingKey();
			
			LoggerUtil.request.debug("[RABBIT] 获取到MQ CONFIG数据,KEY:{},当前处理线程:{}",key,Thread.currentThread().getName());
			processMethodMap.get(key).invoke(this, message);
			LoggerUtil.request.debug("[RABBIT] MQ CONFIG数据处理完成");
		} catch (Exception e) {
			LoggerUtil.error.error(e.getMessage(),e);
			throw new AmqpRejectAndDontRequeueException(e);
		}
	}
	
	@RabbitHandler
	public void onError(byte[] data) {
		StringBuilder sb = new StringBuilder();
		for(byte b : data) {
			sb.append(Byte.toString(b)).append(',');
		}
		
		LoggerUtil.error.error("[{}]获取到无法解析的数据:{}",showQueueName(),sb.toString());
		
		String str = new String(data);
		LoggerUtil.error.debug("尝试解析数据为String:{}",str);
	}
	
	
	private Method getMethod(String name) {
		try {
			Method method = getClass().getDeclaredMethod(name, ConfigRefreshDomain.class);
			method.setAccessible(true);
			return method;
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
}

package com.shziyuan.flow.mq.stream.conf;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.shziyuan.flow.global.util.MD5Util;

/**
 * Rabbit队列的配置
 * @author james.hu
 *
 */
@Configuration
@EnableRabbit
public class RabbitConfiguration {
	// 队列名称
	public enum QUEUE {
		QUEUE_ORDER("flow.queue.supplier.submit"),
		QUEUE_SUPPLIER_REPORT("flow.queue.supplier.report"),
		QUEUE_SUPPLIER_REPORT_PASSIVE("flow.queue.supplier.report.passive"),
		QUEUE_DISTRIBUTOR_PUSH("flow.distributor.push"),
		LOG_DBWRITER("log.dbwriter"),
		QUEUE_MODULE_ORDER("flow.module.order"),
		
		NOTI_CONFIG("flow.noti.config"),
		NOTI_CONFIG_BIND("flow.noti.config.bind"),
		NOTI_ORDER("flow.noti.order");
		
		public String val;
		private QUEUE(String val) {
			this.val = val;
		}
	}
	
	public enum ROUTEKEY {
		NOTI_CONFIG_SUPPLIER("rkey.noti.config.supplier"),
		NOTI_CONFIG_SUPPLIERCODE("rkey.noti.config.suppliercode"),
		NOTI_CONFIG_DISTRIBUTOR("rkey.noti.config.distributor"),
		NOTI_CONFIG_SKU("rkey.noti.config.sku"),
		NOTI_CONFIG_SUPPLIER_BIND("rkey.noti.config.supplier.bind"),
		NOTI_CONFIG_DISTRIBUTOR_BIND("rkey.noti.config.distributor.bind"),
		NOTI_ORDER_MANUAL("rkey.noti.order.manual");
		
		public String val;
		private ROUTEKEY(String val) {
			this.val = val;
		}
	}
	
	public enum BINDING {
		NOTI_CONFIG("rkey.noti.config.*"),
		NOTI_CONFIG_BIND("rkey.noti.config.*.bind"),
		NOTI_ORDER("rkey.noti.order.*");
		
		public String val;
		private BINDING(String val) {
			this.val = val;
		}
	}
	
	
	public enum EXCHANGE {
		LOG("flow.ex.log"),
		NOTI("flow.ex.noti"),
		QUEUE("flow.ex.queue"),
		QUEUE_DELAYED("flow.ex.queue.delayed");
		
		public String val;
		private EXCHANGE(String val) {
			this.val = val;
		}
	}
	
	/**
	 * 产生通知队列时,附加的随机序列码
	 * 每个进程会拥有自己持有的一个通知队列
	 */
	public NotiName notiName;
	private QueueName queueName;
	
	@Autowired
	private Environment environment;
	
	@PostConstruct
	public void init() {
		// 随机序列码
		String notiQueueUUID = MD5Util.MD5Lower32(environment.getProperty("spring.application.name") + System.currentTimeMillis())
				.substring(0,16);
		notiQueueUUID = '.' + notiQueueUUID;
		notiName = new NotiName();
		notiName.setNotiConfig(QUEUE.NOTI_CONFIG.val + notiQueueUUID);
		notiName.setNotiConfigBind(QUEUE.NOTI_CONFIG_BIND.val + notiQueueUUID);
		notiName.setNotiOrder(QUEUE.NOTI_ORDER.val + notiQueueUUID);
		
		// 平台标识
		String suffix = '.' + environment.getProperty("system.platform.name","UNDEF");
		queueName = new QueueName();
		queueName.setSupplierSubmit(QUEUE.QUEUE_ORDER.val + suffix);
		queueName.setSupplierReport(QUEUE.QUEUE_SUPPLIER_REPORT.val + suffix);
		queueName.setSupplierPassiveReport(QUEUE.QUEUE_SUPPLIER_REPORT_PASSIVE.val);
		queueName.setDistributorPush(QUEUE.QUEUE_DISTRIBUTOR_PUSH.val + suffix);
		queueName.setLogDbWriter(QUEUE.LOG_DBWRITER.val);
	}
	
	// 声明Exchange
	@Bean
	public Exchange directLogExchange() {
		return ExchangeBuilder.directExchange(EXCHANGE.LOG.val).durable(true).build();
	}
	
	@Bean
	public Exchange topicNotiExchange() {
		return ExchangeBuilder.topicExchange(EXCHANGE.NOTI.val).durable(true).delayed().build();
	}
	
	@Bean
	public Exchange directQueueExchange() {
		return ExchangeBuilder.directExchange(EXCHANGE.QUEUE.val).durable(true).build();
	}
	
	@Bean
	public Exchange directDelayedQueueExchange() {
		return ExchangeBuilder.directExchange(EXCHANGE.QUEUE_DELAYED.val).durable(true).delayed().build();
	}
	
	
	// 声明RabbitTemplate,注入json转换器
	@Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }
	
	// Rabbit容器工厂
	@Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
//	        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//	        factory.setErrorHandler(new RabbitErrorHandler());
        return factory;
    }
	
	
	// 声明队列
	@Bean
	public Queue queueOrder() {
		// 供应商提交队列,配置为支持死信
		return QueueBuilder.durable(queueName.getSupplierSubmit())
//				.withArgument("x-dead-letter-exchange", EX_DLX_NORMAL)
				.build();
	}
		
	@Bean
	public Queue queueSupplierReport() {
		return QueueBuilder.durable(queueName.getSupplierReport()).build();
	}
	
	@Bean
	public Queue queueSupplierReportPassive() {
		return QueueBuilder.durable(queueName.getSupplierPassiveReport()).build();
	}
	
	@Bean
	public Queue queueDistributorPush() {
		return QueueBuilder.durable(queueName.getDistributorPush()).build();
	}
	
	@Bean
	public Queue logDbWriter() {
		return QueueBuilder.durable(QUEUE.LOG_DBWRITER.val).build();
	}
	
	@Bean
	public Queue queueModuleOrder() {
		return QueueBuilder.durable(QUEUE.QUEUE_MODULE_ORDER.val).build();
	}
	
	/**
	 * 定义所有通知队列
	 * 仅当配置项 system.useRabbitNoti为true时启用, 当程序仅作为生成者时,不需要设置
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(name="system.useRabbitNoti.config",havingValue="true")
	public Queue notiConfig() {
		return QueueBuilder.nonDurable(notiName.getNotiConfig()).autoDelete().build();
	}
	
	@Bean
	@ConditionalOnProperty(name="system.useRabbitNoti.configBind",havingValue="true")
	public Queue notiConfigBind() {
		return QueueBuilder.nonDurable(notiName.getNotiConfigBind()).autoDelete().build();
	}
	
	@Bean
	@ConditionalOnProperty(name="system.useRabbitNoti.order",havingValue="true")
	public Queue notiOrder() {
		return QueueBuilder.nonDurable(notiName.getNotiOrder()).autoDelete().build();
	}
	
	
	// 声明绑定
	@Bean
	public Binding queueOrderBinding() {
		return BindingBuilder
				.bind(queueOrder()).to(directQueueExchange())
				.with(queueName.getSupplierSubmit()).noargs();  
	}
	
	@Bean
	public Binding queueSupplierReportBinding() {
		return BindingBuilder
				.bind(queueSupplierReport()).to(directDelayedQueueExchange())
				.with(queueName.getSupplierReport()).noargs();  
	}
	
	@Bean
	public Binding queueSupplierReportPassiveBinding() {
		return BindingBuilder
				.bind(queueSupplierReportPassive()).to(directQueueExchange())
				.with(queueName.getSupplierPassiveReport()).noargs();  
	}
	
	@Bean
	public Binding queueDistributorPushBinding() {
		return BindingBuilder
				.bind(queueDistributorPush()).to(directDelayedQueueExchange())
				.with(queueName.getDistributorPush()).noargs();
	}
	
	@Bean
	public Binding logBinding() {
		return BindingBuilder.bind(logDbWriter()).to(directLogExchange()).with(QUEUE.LOG_DBWRITER.val).noargs();  
	}
	
	@Bean
	public Binding queueModuleOrderBinding() {
		return BindingBuilder
				.bind(queueModuleOrder()).to(directQueueExchange())
				.with(QUEUE.QUEUE_MODULE_ORDER.val).noargs();
	}
		
	@Bean
	@ConditionalOnProperty(name="system.useRabbitNoti.config",havingValue="true")
	public Binding notiConfigBinding() {
		return BindingBuilder.bind(notiConfig()).to(topicNotiExchange()).with(BINDING.NOTI_CONFIG.val).noargs();
	}
	
	@Bean
	@ConditionalOnProperty(name="system.useRabbitNoti.configBind",havingValue="true")
	public Binding notiConfigBindBinding() {
		return BindingBuilder.bind(notiConfigBind()).to(topicNotiExchange()).with(BINDING.NOTI_CONFIG_BIND.val).noargs();
	}
	
	@Bean
	@ConditionalOnProperty(name="system.useRabbitNoti.order",havingValue="true")
	public Binding notiOrderBinding() {
		return BindingBuilder.bind(notiOrder()).to(topicNotiExchange()).with(BINDING.NOTI_ORDER.val).noargs();
	}

	
	
	
	public QueueName getQueueName() {
		return queueName;
	}
	public NotiName getNotiName() {
		return notiName;
	}
	
	/**
	 * 各平台标识符,用于队列后缀
	 */
	public class QueueName {
		private String supplierSubmit;
		private String supplierReport;
		private String supplierPassiveReport;
		private String distributorPush;
		private String orderManualCmd;
		
		private String logDbWriter;
		
		public String getSupplierSubmit() {
			return supplierSubmit;
		}
		public String getSupplierSubmit(String suffix) {
			return QUEUE.QUEUE_ORDER.val + '.' + suffix;
		}
		public void setSupplierSubmit(String supplierSubmit) {
			this.supplierSubmit = supplierSubmit;
		}
		public String getSupplierReport() {
			return supplierReport;
		}
		public String getSupplierReport(String suffix) {
			return QUEUE.QUEUE_SUPPLIER_REPORT.val + '.' + suffix;
		}
		public void setSupplierReport(String supplierReport) {
			this.supplierReport = supplierReport;
		}
		public String getSupplierPassiveReport() {
			return supplierPassiveReport;
		}
		public void setSupplierPassiveReport(String supplierPassiveReport) {
			this.supplierPassiveReport = supplierPassiveReport;
		}
		public String getDistributorPush() {
			return distributorPush;
		}
		public String getDistributorPush(String suffix) {
			return QUEUE.QUEUE_DISTRIBUTOR_PUSH.val + '.' + suffix;
		}
		public void setDistributorPush(String distributorPush) {
			this.distributorPush = distributorPush;
		}
		public String getOrderManualCmd() {
			return orderManualCmd;
		}
		public void setOrderManualCmd(String orderManualCmd) {
			this.orderManualCmd = orderManualCmd;
		}
		public String getLogDbWriter() {
			return logDbWriter;
		}
		public void setLogDbWriter(String logDbWriter) {
			this.logDbWriter = logDbWriter;
		}
	}
	
	public class NotiName {
		private String notiConfig;
		private String notiConfigBind;
		private String notiOrder;
		public String getNotiConfig() {
			return notiConfig;
		}
		public void setNotiConfig(String notiConfig) {
			this.notiConfig = notiConfig;
		}
		public String getNotiConfigBind() {
			return notiConfigBind;
		}
		public void setNotiConfigBind(String notiConfigBind) {
			this.notiConfigBind = notiConfigBind;
		}
		public String getNotiOrder() {
			return notiOrder;
		}
		public void setNotiOrder(String notiOrder) {
			this.notiOrder = notiOrder;
		}
	}
}

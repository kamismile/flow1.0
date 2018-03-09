package com.shziyuan.flow.queue.base.conf;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * 供应商相关配置
 * @author james.hu
 *
 */
@Configuration
@EnableFeignClients("com.shziyuan.flow.queue.base.supplier")		// 用于加载base级别项目中的Feign接口
public class QueueBaseConfiguration {

}

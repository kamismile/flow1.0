package com.shziyuan.flow.cnfmanager.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.shziyuan.flow.global.domain.action.ActionResponse;
import com.shziyuan.flow.global.util.LoggerUtil;

@Aspect
@Component
public class ActionAspect {
	private Logger logger = LoggerUtil.error;
	
	@Pointcut("execution(public * com.shziyuan.flow.cnfmanager.action.*.*(..))")
    public void action(){

    }
	
	@Around("action()")
	public Object doAround(ProceedingJoinPoint joinPoint) {
        try {
        	return joinPoint.proceed();
        } catch (Throwable e) {
        	logger.error("Action AOP 捕获异常",e);
        	return ActionResponse.error(e.getMessage());
        }
	}
}

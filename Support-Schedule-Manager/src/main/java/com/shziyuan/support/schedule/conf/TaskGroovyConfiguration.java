package com.shziyuan.support.schedule.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.shziyuan.flow.global.groovy.GroovyConfiguration;

@Configuration
@Import(GroovyConfiguration.class)
public class TaskGroovyConfiguration {

}

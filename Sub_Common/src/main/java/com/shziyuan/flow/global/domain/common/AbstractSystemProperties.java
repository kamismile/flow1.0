package com.shziyuan.flow.global.domain.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("system")
public abstract class AbstractSystemProperties {
	private SystemPlatformProperties platform;

	public SystemPlatformProperties getPlatform() {
		return platform;
	}

	public void setPlatform(SystemPlatformProperties platform) {
		this.platform = platform;
	}
}

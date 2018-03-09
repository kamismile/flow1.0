package com.ziyuan.web.distributor.conf;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("whitelist")
public class WhiteListProperties {

	public List<String> ipWhites;

	public List<String> getIpWhites() {
		return ipWhites;
	}

	public void setIpWhites(List<String> ipWhites) {
		this.ipWhites = ipWhites;
	}

}

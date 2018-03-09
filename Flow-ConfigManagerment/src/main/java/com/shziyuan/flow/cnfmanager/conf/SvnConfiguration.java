package com.shziyuan.flow.cnfmanager.conf;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.wc.SVNRevision;

import com.shziyuan.flow.cnfmanager.service.svn.SvnUtilService;
import com.shziyuan.flow.global.util.LoggerUtil;

@Configuration
@ConfigurationProperties(prefix = "config-server.svn-server")
public class SvnConfiguration {
	private String svnRoot;
	private String username;
	private String password;
	
	@Value("${config-server.svn-local.workspace}")
	private String svnWorkspace;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Bean
	public SvnUtilService svnUtilService() {
		SvnUtilService svnUtilService = new SvnUtilService(svnRoot, username, password);
		svnUtilService.authSvn();
		// 检测本地workspace是否存在
		Resource svnWorkspaceResource = applicationContext.getResource("classpath:");
		
		File applicationDoc = null;
		try {
			applicationDoc = svnWorkspaceResource.getFile();
		} catch (IOException e) {
			LoggerUtil.sys.error(e.getMessage());
		}
		
		if(applicationDoc == null || !applicationDoc.exists() || !applicationDoc.isDirectory()) {
			applicationDoc = new File(System.getProperty("user.dir"));
		}
		
		File svnWorkspaceFile = new File(applicationDoc,svnWorkspace);
		
		if(!svnWorkspaceFile.exists()) {
			svnWorkspaceFile.mkdirs();
		}
		
		Assert.isTrue(svnWorkspaceFile.isDirectory(), "指定的svn工作目录路径必须是一个目录 -> " + svnWorkspace);
		
		svnUtilService.setDocRootWorkspace(svnWorkspaceFile);
		
		// 检查工作目录属性
		if(!svnUtilService.isWorkingCopy(svnWorkspaceFile)) {
			LoggerUtil.sys.info("指定的工作目录路径未做svn检出,自动匹配工作路径进行检出 -> {}",svnWorkspaceFile.getAbsolutePath());
			
			svnUtilService.checkout(svnUtilService.getSvnRootUrl(), SVNRevision.HEAD, svnWorkspaceFile, SVNDepth.INFINITY,true);
			LoggerUtil.sys.info("svn -> {} 已检出到 -> {}",svnUtilService.getSvnRootUrl().getPath(),svnWorkspaceFile.getAbsolutePath());
		} else {
			LoggerUtil.sys.info("指定的工作目录路径已找到,进行svn update -> {}",svnWorkspaceFile.getAbsolutePath());
			svnUtilService.update(SVNRevision.HEAD, SVNDepth.INFINITY);
			LoggerUtil.sys.info("svn -> {} 已检出到 -> {}",svnUtilService.getSvnRootUrl().getPath(),svnWorkspaceFile.getAbsolutePath());
		}
		
		
		return svnUtilService;
	}
	
	
	
	// getter / setter
	public String getSvnRoot() {
		return svnRoot;
	}
	public void setSvnRoot(String svnRoot) {
		this.svnRoot = svnRoot;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}

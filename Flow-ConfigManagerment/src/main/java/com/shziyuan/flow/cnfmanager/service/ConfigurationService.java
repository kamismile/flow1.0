package com.shziyuan.flow.cnfmanager.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileCopyUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.shziyuan.flow.cnfmanager.domain.InfoModule;
import com.shziyuan.flow.cnfmanager.domain.InfoPlatform;
import com.shziyuan.flow.cnfmanager.service.svn.SvnUtilService;
import com.shziyuan.flow.cnfmanager.wrap.InfoModuleWrap;
import com.shziyuan.flow.cnfmanager.wrap.InfoPlatformWrap;

@Service
public class ConfigurationService {
	@Autowired
	private InfoModuleWrap infoModuleWrap;
	
	@Autowired
	private InfoPlatformWrap infoPlatformWrap;
	
	@Autowired
	private SvnUtilService svnUtilService;
	
	private YAMLMapper yamlMapper = new YAMLMapper();
	private ObjectMapper objectMapper = new ObjectMapper();
		
	public String getConfigByModuleId(int moduleId) {
		InfoModule m_condition = new InfoModule();
		m_condition.setId(moduleId);
		InfoModule module = infoModuleWrap.selectOne(m_condition);
		InfoPlatform p_conditon = new InfoPlatform();
		p_conditon.setId(module.getPlatformId());
		InfoPlatform platform = infoPlatformWrap.selectOne(p_conditon);
		
		File labelDoc = new File(svnUtilService.getDocRootWorkspace(),module.getType());
		String filename = platform.getPlatform() + "-" + module.getModule() + ".yml";
		File file = new File(labelDoc,filename);
		
		String json = readFile(file);
		return toBase64(json);
	}
	
	private String readFile(File file) {
		FileInputStream fis;
		InputStreamReader reader = null;
		
		try {
			fis = new FileInputStream(file);
			reader = new InputStreamReader(fis, Charset.forName("utf-8"));
			
			String content = FileCopyUtils.copyToString(reader);
			
			return yamlToJson(content);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if(reader != null)
				try {
					reader.close();
				} catch (IOException e) {
				}
		}
		
	}
	
	private String yamlToJson(String yml) throws JsonProcessingException, IOException {
		JsonNode jsonNode = yamlMapper.readTree(yml);
		return objectMapper.writeValueAsString(jsonNode);
	}
	
	private String toBase64(String source) {
		try {
			return Base64Utils.encodeToString(source.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}

package com.ziyuan.web.manager.service.impl;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shziyuan.flow.global.domain.flow.InfoAccountDistributorCertificate;
import com.shziyuan.flow.global.util.TimestampUtil;
import com.ziyuan.web.manager.wrap.ICRUDWrap;
import com.ziyuan.web.manager.wrap.InfoAccountDistributorCertificateWrap;


@Service
public class InfoAccountDistributorCertificateService extends AbstractCRUDService<InfoAccountDistributorCertificate>{

	@Resource
	private InfoAccountDistributorCertificateWrap infoAccountDistributorCertificateWrap;
	
	@Override
	public ByteArrayOutputStream export(JEasyuiRequestBean domain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void sendMQ() {
	}
	
	@Override
	protected String getMQCahceName() {
		return null;
	}

	@Override
	public ICRUDWrap<InfoAccountDistributorCertificate> getWrap() {
		return infoAccountDistributorCertificateWrap;
	}
	
	public JEasyuiData selectByDistributorId(String uploadPath,int distributor_id) {
		//DynamicDataSourceHolder.useSlave();
		try {
			List<InfoAccountDistributorCertificate> list = infoAccountDistributorCertificateWrap.selectByDistributorId(distributor_id);
			list.forEach(idc -> idc.setFilename(uploadPath + idc.getFilename()));
			return new JEasyuiData(list);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
			return new JEasyuiData(false, e.getMessage());
		}
	}
	
//	public JEasyuiData selectByPendingId(String uploadPath,int pending_id) {
//		//DynamicDataSourceHolder.useSlave();
//		try {
//			List<InfoAccountDistributorCertificate> list = infoAccountDistributorCertificateWrap.selectByPendingId(pending_id);
//			list.forEach(idc -> idc.setFilename(uploadPath + idc.getFilename()));
//			return new JEasyuiData(list);
//		} catch (RuntimeException e) {
//			logger.error(e.getMessage(),e);
//			return new JEasyuiData(false, e.getMessage());
//		}
//	}
	
	public void selectByPendingId(String uploadPath, int pending_id, HttpServletRequest request, HttpServletResponse res) throws Exception {
		//DynamicDataSourceHolder.useSlave();
		OutputStream os = null;
		FileInputStream fis = null;
		try {
			List<InfoAccountDistributorCertificate> list = infoAccountDistributorCertificateWrap.selectByPendingId(pending_id);
			list.forEach(idc -> idc.setFilename(uploadPath + idc.getFilename()));
			
			for (InfoAccountDistributorCertificate info : list) {
				
				res.setContentType("text/html; charset=UTF-8");
				res.setContentType("image/jpeg");
				String fname = request.getParameter("filename");
//				String newpath = new String(fname.getBytes("ISO-8859-1"), "UTF-8");
				String absolutePath = info.getFilename();
				fis = new FileInputStream(absolutePath);
				os = res.getOutputStream();
				
				int count = 0;
				byte[] buffer = new byte[1024 * 1024];
				while ((count = fis.read(buffer)) != -1)
					os.write(buffer, 0, count);
				os.flush();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			if (os != null)
				os.close();
			if (fis != null)
				fis.close();
		}
	}
	
	public JEasyuiData insert(String filepath,MultipartFile file,int distributor_id,int pending_id) {
		if (!file.isEmpty()) {  
            try {
                // 文件保存路径  
            	String ext = file.getOriginalFilename();
            	ext = ext.substring(ext.lastIndexOf('.'));
            	InfoAccountDistributorCertificate idc = new InfoAccountDistributorCertificate();
            	idc.setInsert_time(TimestampUtil.now());
            	idc.setPending_id(pending_id);
            	idc.setDistributor_id(distributor_id);
            	idc.setFilename(UUID.randomUUID().toString() + ext);
            	idc.setSourcename(file.getOriginalFilename());
            	if(idc.getSourcename().length() > 50)
            		idc.setSourcename(idc.getSourcename().substring(0,50));
            	
            	File newFile = new File(filepath);
            	if(!newFile.exists()){
            		newFile.mkdirs();
            	}
            	String writePath = filepath + idc.getFilename();
            	System.out.println("writePath:"+writePath);
            	
            	// 转存文件  
            	file.transferTo(new File(writePath));
                super.insert(idc);
                return new JEasyuiData(true, "");
            } catch (Exception e) {  
                return new JEasyuiData(false, e.getMessage());
            }  
        } else
        	return new JEasyuiData(false, "上传文件为空");
	}

	public JEasyuiData delete(String filepath,int id) {
		InfoAccountDistributorCertificate old = infoAccountDistributorCertificateWrap.selectOne(id);
		File file = new File(filepath + old.getFilename());
		file.delete();
		return super.delete(id);
	}

}

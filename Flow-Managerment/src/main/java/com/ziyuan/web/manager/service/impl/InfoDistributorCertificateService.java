package com.ziyuan.web.manager.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import com.shziyuan.flow.global.domain.flow.InfoDistributorCertificate;
import com.ziyuan.web.manager.wrap.ICRUDWrap;
import com.ziyuan.web.manager.wrap.InfoDistributorCertificateWrap;



@Service
public class InfoDistributorCertificateService extends AbstractCRUDService<InfoDistributorCertificate>{

	@Resource
	private InfoDistributorCertificateWrap infoDistributorCertificateWrap;
	
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
	public ICRUDWrap<InfoDistributorCertificate> getWrap() {
		return infoDistributorCertificateWrap;
	}
	
//	public JEasyuiData selectByDistributorId(int distributor_id) {
//		//DynamicDataSourceHolder.useSlave();
//		try {
//			return new JEasyuiData(infoDistributorCertificateWrap.selectByDistributorId(distributor_id));
//		} catch (RuntimeException e) {
//			logger.error(e.getMessage(),e);
//			return new JEasyuiData(false, e.getMessage());
//		}
//	}
	
	public void selectByDistributorId(HttpServletRequest request, HttpServletResponse res, int distributor_id, String uploadPath) throws Exception {
		
		OutputStream os = null;
		FileInputStream fis = null;
		try {
			List<InfoDistributorCertificate> list = infoDistributorCertificateWrap.selectByDistributorId(distributor_id);
			list.forEach(idc -> idc.setFilename(uploadPath + idc.getFilename()));
			
			for (InfoDistributorCertificate info : list) {
				
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
	
	public JEasyuiData insert(String filepath,MultipartFile file,int distributor_id) {
		if (!file.isEmpty()) {  
            try {
                // 文件保存路径  
            	String ext = file.getOriginalFilename();
            	ext = ext.substring(ext.lastIndexOf('.'));
            	InfoDistributorCertificate idc = new InfoDistributorCertificate();
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
		InfoDistributorCertificate old = infoDistributorCertificateWrap.selectOne(id);
		File file = new File(filepath + old.getFilename());
		file.delete();
		return super.delete(id);
	}

	/**
	 * 下载凭证
	 * @param id
	 * @param res
	 * @throws UnsupportedEncodingException 
	 */
	public void downloadCertificate(int id, HttpServletResponse response, String path) throws UnsupportedEncodingException {
		InfoDistributorCertificate old = infoDistributorCertificateWrap.selectOne(id);
		File file = new File(path + old.getFilename());
		
		if (file.exists()) {
			
			response.setHeader("content-type", "application/octet-stream");
			response.setContentType("application/octet-stream");
			//解决中文乱码问题
			String filename = new String(file.getName().getBytes("utf-8"),"iso-8859-1");
			//设置文件名
			response.addHeader("Content-Disposition", "attachment;fileName="+filename);
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }
	
}

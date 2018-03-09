//package com.ziyuan.web.manager.action.ext;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.Socket;
//import java.net.UnknownHostException;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//
//import org.springframework.context.ApplicationContext;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.shziyuan.flow.global.common.Constant;
//import com.shziyuan.flow.global.domain.probe.DWIProbe;
//import com.ziyuan.global.util.probe.AbstractProbeAction;
//
//
//
//@CrossOrigin
//@Controller
//@RequestMapping("/ext")
//public class ProbeAction extends AbstractProbeAction{
//	@Resource
//	private DataSource dataSource;
//	
//	@Override
//	@RequestMapping("/probe/check")
//	@ResponseBody
//	public DWIProbe check() {
//		return super.check();
//	}
//	
//	@Override
//	protected void systemConstant(DWIProbe probe, ApplicationContext ctx) {
//		probe.putSystem("system_mark", Constant.SYSTEM_MARK);
//	}
//	@Override
//	protected Connection _connectionMaster() throws SQLException {
//		
//		return dataSource.getConnection();
//	}
//	@Override
//	protected Connection _connectionSlave() throws SQLException {
//		//DynamicDataSourceHolder.useSlave();
//		return dataSource.getConnection();
//	}
//	
//	
//	/**
//	 * Proxy
//	 */
//	@RequestMapping(value = "/probe/proxy",produces="application/json")
//	@ResponseBody
//	public String probeProxy(String url,String platformName) {
//		String host = url.substring(0,url.indexOf(":"));
//		int port = Integer.parseInt(url.substring(url.indexOf(":") + 1));
//		
//		String resp;
//		InputStream is = null;
//		InputStreamReader reader;
//		try {
//			Socket socket = new Socket(host,port);
//			is = socket.getInputStream();
//			reader = new InputStreamReader(is);
//			char[] buf = new char[1024];
//			int size = 0;
//			StringBuilder sb = new StringBuilder();
//			while((size = reader.read(buf)) != -1)
//				sb.append(buf,0,size);
//			return sb.toString();
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			if(is != null)
//				try {
//					is.close();
//				} catch (IOException e) {
//				}
//		}
//		
//		return "";
//	}
//}

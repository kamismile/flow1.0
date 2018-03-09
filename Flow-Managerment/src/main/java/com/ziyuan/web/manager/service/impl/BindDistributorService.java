package com.ziyuan.web.manager.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.flow.global.jeasyui.JEasyuiRequestBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shziyuan.flow.global.common.CacheDefine;
import com.shziyuan.flow.global.domain.flow.BindDistributor;
import com.shziyuan.flow.global.domain.flow.BindSupplier;
import com.shziyuan.flow.global.domain.flow.DistributorOrderBatch;
import com.shziyuan.flow.global.domain.flow.InfoDistributor;
import com.shziyuan.flow.global.domain.flow.dwi.DistributorOrder;
import com.shziyuan.flow.global.domain.stream.ConfigRefreshDomain;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.global.util.SignatureUtil;
import com.shziyuan.flow.global.util.TimestampUtil;
import com.shziyuan.flow.mq.stream.producer.StreamConfigOutputService;
import com.ziyuan.web.manager.conf.WebConstant;
import com.ziyuan.web.manager.dao.BindDistributorMapper;
import com.ziyuan.web.manager.domain.AutoFlowBean;
import com.ziyuan.web.manager.domain.BindDistributorBean;
import com.ziyuan.web.manager.domain.BindDistributorChangeBean;
import com.ziyuan.web.manager.domain.InfoDistributorBean;
import com.ziyuan.web.manager.domain.InfoSkuBean;
import com.ziyuan.web.manager.feign.OrderFeign;
import com.ziyuan.web.manager.service.excel.NormallyExcelExporter;
import com.ziyuan.web.manager.service.excel.RowFunction;
import com.ziyuan.web.manager.utils.ExcelData;
import com.ziyuan.web.manager.utils.ExcelRow;
import com.ziyuan.web.manager.utils.ExcelTools;
import com.ziyuan.web.manager.utils.FlowMqDefine;
import com.ziyuan.web.manager.utils.JacksonUtil;
import com.ziyuan.web.manager.utils.ReadExcelUtil;
import com.ziyuan.web.manager.utils.ReflectionToString;
import com.ziyuan.web.manager.wrap.BindDistributorWrap;
import com.ziyuan.web.manager.wrap.ICRUDWrap;
import com.ziyuan.web.manager.wrap.InfoDistributorWrap;

@Service
public class BindDistributorService extends AbstractCRUDService<BindDistributor>{

	@Resource
	private BindDistributorWrap bindDistributorWrap;
	
	@Resource
	private InfoDistributorWrap infoDistributorWrap;
	
	@Resource
	private OrderFeign orderFeign;
	
	@Autowired
	private StreamConfigOutputService streamConfigOutputService;
	
	@Override
	public ICRUDWrap<BindDistributor> getWrap() {
		return bindDistributorWrap;
	}
	
	@Override
	protected String getMQCahceName() {
		return FlowMqDefine.CHANGE_BIND_DISTRIBUTOR;
	}
	
	public JEasyuiData selectBind(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();
		return bindDistributorWrap.selectBind(bean);
	}
	
	public JEasyuiData changeBinding(Principal user, BindDistributorChangeBean data) {
		
		ConfigRefreshDomain configRefreshDomain = new ConfigRefreshDomain();
		
		List<BindDistributor> newlist = data.getNewBind();
		List<BindDistributor> editlist = data.getEditBind();
		List<BindDistributor> removelist = data.getRemoveBind();
		
		data.getNewBind().forEach(bind -> bind.setEnabled(true));
		if(removelist.size() > 0){
			LoggerUtil.request.info("用户[{}], 进行了渠道绑定产品-删除操作, 服务类[{}], 数据[{}]",
					user.getName(), getClass(), ReflectionToString.reflectionToString(data.getRemoveBind().toString()));
			bindDistributorWrap.batchDelete(BindDistributorMapper.class.getName(),data.getRemoveBind(),(domain) -> domain.getId());
			List<Integer> ids = new ArrayList<>();
			for (BindDistributor remove : removelist) {
				ids.add(remove.getId());
			}
			configRefreshDomain.setIds(ids);
			streamConfigOutputService.changeBindDistributor(configRefreshDomain);
		}
		if(editlist.size() > 0){
			LoggerUtil.request.info("用户[{}], 进行了渠道绑定产品-修改操作, 服务类[{}], 数据[{}]",
					user.getName(), getClass(), ReflectionToString.reflectionToString(data.getEditBind().toString()));
			bindDistributorWrap.batchUpdate(BindDistributorMapper.class.getName(),data.getEditBind());
			List<Integer> ids = new ArrayList<>();
			for (BindDistributor edit : editlist) {
				ids.add(edit.getId());
			}
			configRefreshDomain.setIds(ids);
			streamConfigOutputService.changeBindDistributor(configRefreshDomain);
		}
		if(newlist.size() > 0){
			LoggerUtil.request.info("用户[{}], 进行了渠道绑定产品-新建操作, 服务类[{}], 数据[{}]",
					user.getName(), getClass(), ReflectionToString.reflectionToString(data.getNewBind().toString()));
			bindDistributorWrap.batchInsert(BindDistributorMapper.class.getName(),data.getNewBind());
			List<Integer> ids = new ArrayList<>();
			for (BindDistributor list : newlist) {
				ids.add(list.getId());
			}
			configRefreshDomain.setIds(ids);
			streamConfigOutputService.changeBindDistributor(configRefreshDomain);
		}
		
		return new JEasyuiData(true,"");
	}

	@Override
	public ByteArrayOutputStream export(JEasyuiRequestBean domain) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public JEasyuiData selectBusinessSkus(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();
		return bindDistributorWrap.selectBusinessSkus(bean);
	}
	
	public JEasyuiData selectBusinessSkusPages(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();
		return bindDistributorWrap.selectBusinessSkusPages(bean);
	}
	
	public ByteArrayOutputStream exportBusinessSkus(Principal user, JEasyuiRequestBean bean) {
		// TODO Auto-generated method stub
		int id = infoDistributorWrap.selectIDByName(user.getName());
		bean.getParam().put("id", String.valueOf(id));
		ExcelRow heads = ExcelTools.excelHeaders("渠道名称","省份","运营商","产品编码","类型","业务类型","档次","面值(元)","折扣");
		ExcelData data = null;
		List<InfoSkuBean> list = (List<InfoSkuBean>) selectBusinessSkus(bean).getRows();
		if (list!=null && list.size() !=0) {
			data = new ExcelData(list.size());
			for (int i = 0; i < list.size(); i++) {
				InfoSkuBean infoSkuBean = list.get(i);
				ExcelRow row = new ExcelRow();
				row.add(infoSkuBean.getDistributor_name());
				row.add(infoSkuBean.getProvince());
				row.add(infoSkuBean.getOperator());
				row.add(infoSkuBean.getSku());
				row.add(infoSkuBean.getScope_name());
				row.add(infoSkuBean.getInfo_ptype());
				row.add(infoSkuBean.getPkgsize());
				row.add(infoSkuBean.getPrice());
				row.add(infoSkuBean.getDiscount());
				
				data.add(row);
			}
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ExcelTools.writeToXLSX(heads, data, bean.getParam().get("filename"), out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;	
	}
	
	public JEasyuiData test(JEasyuiRequestBean bean) {
		String data = bean.getParam().get("data");
		try {
			String result = Request.Post(WebConstant.DWI_ORDER_INTERFACE_TEST)
					.bodyString(data, ContentType.APPLICATION_JSON)
					.execute().returnContent().asString();
				logger.debug(result);
			String msg = (String) JacksonUtil.StringToJson(result).get("msg");
				return new JEasyuiData(true, result);
		} catch (Exception e) {
			return new JEasyuiData(false,e.getMessage());
		}
	}

	public JEasyuiData order(AutoFlowBean bean, HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			String code = String.valueOf(session.getAttribute("orderCode"));
			if(!bean.getCode().equals(code)){
				return new JEasyuiData(false, "验证码不正确");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new JEasyuiData(false,e.getMessage());
		}
		InfoDistributor dis = infoDistributorWrap.selectOne(bean.getId());
		List<String> phoneList = bean.getPhone();
		List<String> errorList = new ArrayList<>();
		Map obj = null;
		for (String phone : phoneList) {
			DistributorOrder param = new DistributorOrder();
			param.setApiKey(dis.getCode());
			String orderno = createPlatformOrderNo();
			param.setCstmOrderNo(orderno);
			param.setPhone(phone);
			param.setTimeStamp(TimestampUtil.nowString());
			param.setProductCode(bean.getSku());
			SignatureUtil signatureUtil = new SignatureUtil();
			param.setSign(signatureUtil.sign(param, dis.getKey()));

			try {
				obj = (Map)orderFeign.orderAutoFlow(param);
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
				errorList.add(phone+"充值失败");
			}
			
			if(!obj.get("code").equals("0000")){
				errorList.add(phone+"充值失败");
			}
		}
		if(errorList.size()==0){
			errorList.add("充值成功");
		}
		return new JEasyuiData(errorList);

	}
	
	public JEasyuiData orderBatch(Map<String,Object> bean) {
		InfoDistributor dis = infoDistributorWrap.selectOne((int) bean.get("username"));
		DistributorOrderBatch param = new DistributorOrderBatch();
		param.setApiKey(dis.getCode());
		String orderno = createPlatformOrderNo();
		param.setCstmOrderNo(orderno);
		param.setPhone("");
		param.setTimeStamp(TimestampUtil.nowString());
		param.setProductCode((String) bean.get("sku"));
		SignatureUtil signatureUtil = new SignatureUtil();
		param.setSign(signatureUtil.sign(param, dis.getKey()));
		List<String> phones = (List<String>) bean.get("phones");
		param.setPhones(phones.toArray(new String[]{}));
		ObjectMapper objectMapper = new ObjectMapper();
		String data;
		try {
			data = objectMapper.writeValueAsString(param);
			String result = Request.Post(WebConstant.DWI_ORDER_INTERFACE + "/batch")
					.bodyString(data, ContentType.APPLICATION_JSON)
					.execute().returnContent().asString();
				logger.debug(result);
			String msg = (String) JacksonUtil.StringToJson(result).get("msg");
				return new JEasyuiData(true, msg);
		} catch (Exception e) {
			return new JEasyuiData(false,e.getMessage());
		}
	}
	
	private String createPlatformOrderNo() {
		//时间戳
		long now = System.currentTimeMillis();
		//随机序列号
		int i = new Random().nextInt(10000);
		
		//格式化订单号（时间戳+自增序号）
		StringBuilder sb = new StringBuilder();
		sb.append(now).append(i);
		
		return sb.toString();
	}
	
	
	public JEasyuiData selectTreeBySku() {
		//DynamicDataSourceHolder.useSlave();
		return new JEasyuiData(bindDistributorWrap.selectTreeBySku());
	}
	public JEasyuiData selectTreeByDistributor() {
		//DynamicDataSourceHolder.useSlave();
		return new JEasyuiData(bindDistributorWrap.selectTreeByDistributor());
	}
	
	public JEasyuiData selectTreeTable(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();
		return bindDistributorWrap.selectTreeTable(bean);
	}
	
	public JEasyuiData selectBind2_binded(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();
		return bindDistributorWrap.selectBind2_binded(bean);
	}
	
	public JEasyuiData selectBind2_nonbinded(JEasyuiRequestBean bean) {
		//DynamicDataSourceHolder.useSlave();
		return bindDistributorWrap.selectBind2_nonbinded(bean);
	}
	
	public JEasyuiData selectTreeInBind2() {
		//DynamicDataSourceHolder.useSlave();
		return new JEasyuiData(bindDistributorWrap.selectTreeInBind2());
	}

	public ArrayList<ArrayList<Object>> readExcel(MultipartFile file, String fileName) {
		// TODO Auto-generated method stub
		File uploadDir = new  File("E:/excel");
		//创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）  
	    if (!uploadDir.exists()) uploadDir.mkdirs();
	    File tempFile = null;
	    //判断excel版本
	    if(file.getOriginalFilename().endsWith("xlsx")){
	    	tempFile = new File("E:/excel" + new Date().getTime() + ".xlsx");
	    }else{
	    	 //新建一个临时文件  
		    tempFile = new File("E:/excel" + new Date().getTime() + ".xls");
	    }
	    //初始化输入流  
	    InputStream is = null;
	    ArrayList<ArrayList<Object>> list = null;
	    try {
	    	//将上传的文件写入新建的文件中  
	        file.transferTo(tempFile);
	        
	        list = ReadExcelUtil.readExcel(tempFile);
	        
	        tempFile.delete();
	        
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			 if(is !=null)  
	          {  
	              try{  
	                  is.close();  
	              }catch(IOException e){  
	                  is = null;      
	                  e.printStackTrace();    
	              }  
	          } 
		}
	    return list;
	}

	public ByteArrayOutputStream exportTreeTable(JEasyuiRequestBean bean) {
		List<BindDistributorBean> dataList = bindDistributorWrap.exportTreeTable(bean);
		String sheetname = "渠道商折扣调整";
		String[] headers = {"渠道商","平台产品","运营商","省份","折扣","单价",
				"状态","颗粒度","产品代码","调整折扣","调整进价","生效时间"};
		RowFunction<BindDistributorBean> rowAction = (distributor,row) -> {
			BindDistributorBean bind = (BindDistributorBean)distributor;
			row.add(bind.getDistributor().getName());
			row.add(bind.getSkus().getName());
			row.add(bind.getSkus().getOperator());
			row.add(bind.getSkus().getProvinceid());
			row.add(bind.getDiscount());
			row.add(bind.getPrice());
			row.add(bind.isEnabled());
			row.add(bind.getSkus().getPkgsize());
			row.add(bind.getSkus().getSku());
			row.add(bind.getChange().getDiscount());
			row.add(bind.getChange().getPrice());
			row.add(bind.getChange().getEffective());
			return 0;
		};
		NormallyExcelExporter<BindDistributorBean> exporter = new NormallyExcelExporter<>(sheetname, headers, rowAction);
		try {
			return exporter.export(dataList);
		} catch (IOException e) {
			logger.error("导出渠道商折扣调整错误",e);
			return null;
		}
	}
}

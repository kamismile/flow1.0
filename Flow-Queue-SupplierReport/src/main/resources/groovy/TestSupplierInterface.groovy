import com.shziyuan.flow.global.domain.flow.InfoSupplier
import com.shziyuan.flow.global.domain.flow.InfoSupplierCodetable
import com.shziyuan.flow.global.domain.flow.Order
import com.shziyuan.flow.global.domain.flow.wraped.QueueOrderMQWrap
import com.shziyuan.flow.global.executor.IInterfaceRequestResponse;
import com.shziyuan.flow.global.executor.IInterfaceRequestResponse.Processing;
import com.shziyuan.flow.queue.base.interactive.AbstractRestSupplieInterface
import com.shziyuan.flow.queue.base.interactive.JsonInterfaceRequestResponse

import java.util.Map

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate


class TestSupplierInterface extends AbstractRestSupplieInterface {
		
	protected JsonInterfaceRequestResponse doSubmit(RestTemplate restTemplate,Order order,InfoSupplier sup,InfoSupplierCodetable supcode,Map<String,Object> attrMap) {
		println "test supplier submit11";
		
		String url = attrMap.get("submitUrl");
		println url
		
//		String source = restTemplate.getForObject(url, String.class);
//		
//		JsonInterfaceRequestResponse resp = new JsonInterfaceRequestResponse(source);
//		resp.success(source,"ok","this is ok");
		
		JsonInterfaceRequestResponse resp = postJson(url,"success");
		
		String code = resp.getInterfaceInput().get("code");
		String msg = resp.getInterfaceInput().get("msg");
		
		if("success".equals(code))
			resp.success(code,msg);
		else
			resp.faild(code,msg);
		println resp.getProcessing();
		
		resp.setPassiveRedisKey(resp.getInterfaceInput().get("key"));
		return resp;
	}
	
	@Override
	protected JsonInterfaceRequestResponse doReport(RestTemplate restTemplate, Order order, InfoSupplier sup,
			InfoSupplierCodetable supcode, Map<String, Object> attrMap) {
		String url = attrMap.get("reportUrl");
		JsonInterfaceRequestResponse resp = postJson(url,"faild");
		
		String code = resp.getInterfaceInput().get("code");
		String msg = resp.getInterfaceInput().get("msg");
		
		if("success".equals(code))
			resp.success(code,msg);
		else if("hold".equals(code))
			resp.hold(code,msg);
		else
			resp.faild(code,msg);
		println resp.getProcessing();
		
		return resp;
	}
	
	protected JsonInterfaceRequestResponse doPassiveReportResult(RestTemplate restTemplate, Order order, InfoSupplier sup,
		 InfoSupplierCodetable supcode, Map attrMap, String reportSource) {
		 JsonInterfaceRequestResponse resp = new JsonInterfaceRequestResponse(reportSource);
		 resp.parseSource();
		 
		 String code = resp.getInterfaceInput().get("code");
		 String msg = resp.getInterfaceInput().get("msg");
		 
		 if("success".equals(code))
			 resp.success(code,msg);
		 else if("faild".equals(code))
			 resp.faild(code,msg);
		 else
			 resp.hold(code, msg);
			 
		return resp;
	};
	
	public String parsePassiveReportKey(String reportSource) {
		return "12345678";
	}
}
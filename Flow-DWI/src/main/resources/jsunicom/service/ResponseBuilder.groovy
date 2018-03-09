
import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map

import javax.annotation.PostConstruct

import org.springframework.beans.factory.annotation.Autowired;

import com.shziyuan.flow.global.domain.common.Status
import com.shziyuan.flow.global.domain.common.StatusCode
import com.shziyuan.flow.global.domain.flow.Order;
import com.ziyuan.web.distributor.domain.resp.JsunicomResponseData
import com.shziyuan.flow.global.domain.common.Status;
import com.shziyuan.flow.global.domain.common.StatusCode;

public class CCResponseBuilder implements ResponseBuilder{

	@Autowired
	private StatusCode statusCode;
	
	@Override
	public Object success(Order order) {
		JsunicomResponseData data = new JsunicomResponseData();
		Status status = statusCode.getDwi().getSuccess();
		data.setErrorCode(status.code);
		data.setErrorMsg(status.msg);
		data.setSerialno(order.getOrder_no());
		Map<String, String> resultMap = new HashMap<>();
		List<HashMap<String, String>> resultListMap = new ArrayList<HashMap<String, String>>();
		resultMap.put("phone", order.getPhone());
		resultMap.put("businessCode", order.getSku());
		resultMap.put("resultCode", order.getStatus_submit_code());
		
		resultMap.put("resultDescription", "");
		resultMap.put("remark", order.getClient_order_no());
		resultListMap.add((HashMap<String, String>) resultMap);
		data.setResult(resultListMap);
		
		return data;
	}

	@Override
	public Object faild(Status status) {
		JsunicomResponseData res = new JsunicomResponseData();
		res.setErrorCode(status.getCode());
		res.setErrorMsg(status.getMsg());
		res.setResult(null);
		res.setSerialno("");
		return res;
	}

}

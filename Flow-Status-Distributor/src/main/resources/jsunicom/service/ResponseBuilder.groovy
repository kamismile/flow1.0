import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.shziyuan.flow.global.domain.flow.AccountDistributor;
import com.shziyuan.flow.global.domain.common.Status;
import com.shziyuan.flow.global.domain.common.StatusCode;
import com.shziyuan.flow.global.domain.flow.Order;
import com.ziyuan.web.distributor.domain.resp.JsunicomResponseData;
import com.ziyuan.web.distributor.service.ResponseBuilder;

public class JSResponseBuilder implements ResponseBuilder{

	@Autowired
	private StatusCode statusCode;
	
	@Override
	public Object success(Order order) {
		JsunicomResponseData data = new JsunicomResponseData();
		Status status = statusCode.getDwi().getSuccess();
		data.setErrorCode(status.getCode());
		data.setErrorMsg(status.getMsg());
		data.setSerialno(order.getOrder_no());
		Map<String, String> resultMap = new HashMap<>();
		List<HashMap<String, String>> resultListMap = new ArrayList<HashMap<String, String>>();
		if (order.getStatus_report_code() == null || order.getStatus_report_code() == "") {
			resultMap.put("resultCode", order.getStatus_submit_code());
		} else {
			resultMap.put("resultCode", order.getStatus_report_code());
		}
		resultMap.put("resultDescription", "");
		resultListMap.add((HashMap<String, String>) resultMap);
		data.setResult(resultListMap);
		
		return data;
	}
	
	@Override
	public Object success(AccountDistributor account) {
		JsunicomResponseData data = new JsunicomResponseData();
		Status status = statusCode.getDwi().getSuccess();
		data.setErrorCode(status.getCode());
		data.setErrorMsg(status.getMsg());
		Map<String, String> resultMap = new HashMap<>();
		List<HashMap<String, String>> resultListMap = new ArrayList<HashMap<String, String>>();
		resultMap.put("balance", account.getBanlance()+"");
		resultMap.put("present_banlance", account.getPresent_banlance()+"");
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

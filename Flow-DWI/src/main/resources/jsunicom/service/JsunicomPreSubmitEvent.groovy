
import com.ziyuan.web.distributor.action.JsunicomAction
import com.ziyuan.web.distributor.domain.ThirdpartSubmitEventResult;
import com.ziyuan.web.distributor.domain.resp.JsunicomOrderData
import com.ziyuan.web.distributor.service.PreSubmitEvent;

public class JsunicomPreSubmitEvent implements PreSubmitEvent{

	@Override
	public ThirdpartSubmitEventResult onBeforeSubmit(Object submit) {
		JsunicomOrderData jssubmit = (JsunicomOrderData)submit;
		
		ThirdpartSubmitEventResult result = new ThirdpartSubmitEventResult();
		result.setNeedSubmit(JsunicomAction.ORDER_SKU.equals(jssubmit.getServiceName()));
		result.setThirdpartResult(null);
		
		return result;
	}

}

import javax.annotation.PostConstruct

import org.springframework.stereotype.Component

import com.shziyuan.flow.global.domain.flow.dwi.DistributorOrder;
import com.ziyuan.web.distributor.domain.resp.JsunicomOrderData;
import com.ziyuan.web.distributor.service.SubmitConverter;

@Component("jsunicomSubmitConvert")
public class JsunicomSubmitConvert implements SubmitConverter<JsunicomOrderData>{

	@Override
	public DistributorOrder convertSubmit(JsunicomOrderData submit) {
		DistributorOrder order = new DistributorOrder();
		order.setPhone(submit.getParam().get("mob"));
		order.setClientCode(submit.getPartnerId());
		order.setClientOrderNo(submit.getParam().get("remark"));
		order.setProductCode(submit.getParam().get("businessCode"));
		order.setRemote_ip(submit.getRemote_ip());
		order.setSign(submit.getEncryptStr());
		order.setTimeStamp(submit.getTimeStamp());
		
		return order;
	}

}
import org.springframework.stereotype.Component;
import com.ziyuan.web.distributor.domain.DistributorStatus;
import com.ziyuan.web.distributor.domain.resp.JsunicomOrderData;
import com.ziyuan.web.distributor.service.SubmitConverter;

@Component("jsunicomSubmitConvert")
public class JsunicomSubmitConvert implements SubmitConverter<JsunicomOrderData>{

	@Override
	public DistributorStatus convertSubmit(JsunicomOrderData submit) {
		DistributorStatus status = new DistributorStatus();
		status.setClientCode(submit.getPartnerId());
		status.setRemote_ip(submit.getRemote_ip());
		status.setSign(submit.getEncryptStr());
		status.setTimestamp(submit.getTimeStamp());
		
		return status;
	}

}
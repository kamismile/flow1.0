
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.shziyuan.flow.global.domain.flow.dwi.DistributorOrder;
import com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.ziyuan.web.distributor.service.cc.CCSignUtil;
import com.ziyuan.web.distributor.service.filter.Filter;
import com.ziyuan.web.distributor.service.filter.FilterChain;

public class CCSignFilter extends Filter{

	@Autowired
	private CCSignUtil ccSignUtil;
	
	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, DistributorOrder submit,
			ConfiguredBindBean config, FilterChain fc) {
		String realSignature = ccSignUtil.sign(submit, config.getBindDistributor().getDistributor().getKey());
		
		if (realSignature.equals(submit.getSign())) {
			fc.doFilter(request, response, submit, config);
		} else {
			LoggerUtil.sys.info("无效的订单[{}:提-{} | 平-{}],签名无效",submit.getClientCode(),submit.getSign(),realSignature);
			fc.doCheckFaild(getStatusCode().getDwi().getInvalidSign());
		}
	}

}

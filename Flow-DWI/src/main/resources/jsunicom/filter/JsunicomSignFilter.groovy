
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.shziyuan.flow.global.domain.flow.dwi.DistributorOrder;
import com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.global.util.MD5Util
import com.ziyuan.web.distributor.action.JsunicomAction
import com.ziyuan.web.distributor.service.cc.CCSignUtil;
import com.ziyuan.web.distributor.service.filter.Filter;
import com.ziyuan.web.distributor.service.filter.FilterChain;

public class JsunicomSignFilter extends Filter{

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, DistributorOrder submit,
			ConfiguredBindBean config, FilterChain fc) {
		StringBuilder sb = new StringBuilder();
		sb.append(submit.getClientCode())
		  .append(submit.getTimestamp())
		  .append(JsunicomAction.ORDER_SKU)
		  .append(config.getBindDistributor().getDistributor().getKey());
		
		String realSignature = MD5Util.MD5Upper32(sb.toString());
			
		if (realSignature.equals(submit.getSign())) {
			fc.doFilter(request, response, submit, config);
		} else {
			LoggerUtil.sys.info("无效的订单[{}:提-{} | 平-{}],签名无效",submit.getClientCode(),submit.getSign(),realSignature);
			fc.doCheckFaild(getStatusCode().getDwi().getInvalidSign());
		}
	}

}

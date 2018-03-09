
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
			
		if (config.getSubmobile().getProvinceid() == 25) {
			fc.doFilter(request, response, submit, config);
		} else {
			LoggerUtil.sys.info("无效的订单 非江苏号码[{},{},{}]",submit.getClientCode(),submit.getPhone(),config.getSubmobile().getProvinceid());

			fc.doCheckFaild(getStatusCode().getDwi().getNotJsunicom());
		}
	}

}

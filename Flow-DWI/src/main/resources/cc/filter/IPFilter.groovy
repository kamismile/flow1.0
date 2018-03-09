
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.shziyuan.flow.global.domain.flow.InfoDistributorIpAuthority
import com.shziyuan.flow.global.domain.flow.dwi.DistributorOrder;
import com.shziyuan.flow.global.domain.flow.wraped.ConfiguredBindBean;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.ziyuan.web.distributor.conf.WhiteListProperties;
import com.ziyuan.web.distributor.service.filter.Filter;
import com.ziyuan.web.distributor.service.filter.FilterChain;

public class IPFilter extends Filter{

	@Autowired
	private WhiteListProperties whiteListProperties;
	
	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, DistributorOrder submit,
			ConfiguredBindBean config,FilterChain fc) {
		
		String disIp = submit.getRemote_ip();
	
		if(config.getBindDistributor().getDistributor().containIp("*") || whiteListProperties.getIpWhites().contains(disIp)) {
			LoggerUtil.sys.info("配置IP范围*或白名单[{}:{}:{}:来源-{}]",submit.getClientCode(),submit.getClientOrderNo(),submit.getProductCode(),disIp);
			fc.doFilter(request, response, submit,config);
			return;
		}
		
		
		if(!config.getBindDistributor().getDistributor().containIp(disIp)) {
			LoggerUtil.sys.info("无效的订单[{}:{}:{}:来源-{}],ip鉴权失败",submit.getClientCode(),submit.getClientOrderNo(),submit.getProductCode(),disIp);
			fc.doCheckFaild(getStatusCode().getDwi().getInvalidIp());
		} else
			fc.doFilter(request, response, submit,config);
	}
}
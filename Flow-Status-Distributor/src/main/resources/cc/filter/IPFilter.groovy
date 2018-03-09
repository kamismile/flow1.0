import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.ziyuan.web.distributor.conf.WhiteListProperties;
import com.ziyuan.web.distributor.domain.DistributorStatus;
import com.ziyuan.web.distributor.domain.MoreDistributor;
import com.ziyuan.web.distributor.service.filter.Filter;
import com.ziyuan.web.distributor.service.filter.FilterChain;

public class IPFilter extends Filter{

	@Autowired
	private WhiteListProperties whiteListProperties;
	
	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, DistributorStatus status,MoreDistributor more,FilterChain fc) {
		
		String disIp = status.getRemote_ip();
	
		if(more.getIpAuthoritys().contains("*") || whiteListProperties.getIpWhites().contains(disIp)) {
			LoggerUtil.sys.info("配置IP范围*或白名单[{}:来源-{}]",status.getClientCode(),disIp);
			fc.doFilter(request, response, status,more);
			return;
		}
		
		
		if(!more.getIpAuthoritys().contains(disIp)) {
			LoggerUtil.sys.info("无效的订单[{}:来源-{}],ip鉴权失败",status.getClientCode(),disIp);
			fc.doCheckFaild(getStatusCode().getDwi().getInvalidIp());
		} else
			fc.doFilter(request, response, status,more);
	}
}
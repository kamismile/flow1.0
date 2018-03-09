import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.ziyuan.web.distributor.domain.DistributorStatus;
import com.ziyuan.web.distributor.domain.MoreDistributor;
import com.ziyuan.web.distributor.service.cc.CCSignUtil;
import com.ziyuan.web.distributor.service.filter.Filter;
import com.ziyuan.web.distributor.service.filter.FilterChain;

public class CCSignFilter extends Filter{

	@Autowired
	private CCSignUtil ccSignUtil;
	
	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, DistributorStatus status,MoreDistributor more, FilterChain fc) {
		String realSignature = ccSignUtil.sign(status, more.getKey());
		
		if (realSignature.equals(status.getSign())) {
			fc.doFilter(request, response, status, more);
		} else {
			LoggerUtil.sys.info("无效的订单[{}:提-{} | 平-{}],签名无效",status.getClientCode(),status.getSign(),realSignature);
			fc.doCheckFaild(getStatusCode().getDwi().getInvalidSign());
		}
	}

}

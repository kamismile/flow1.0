import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.shziyuan.flow.global.util.LoggerUtil;
import com.shziyuan.flow.global.util.MD5Util;
import com.ziyuan.web.distributor.action.JSStatusAction;
import com.ziyuan.web.distributor.domain.DistributorStatus;
import com.ziyuan.web.distributor.domain.MoreDistributor;
import com.ziyuan.web.distributor.service.filter.Filter;
import com.ziyuan.web.distributor.service.filter.FilterChain;

public class JsunicomSignFilter extends Filter{

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, DistributorStatus status,MoreDistributor more, FilterChain fc) {
		StringBuilder sb = new StringBuilder();
		sb.append(status.getClientCode())
		  .append(status.getTimestamp())
		  .append(JSStatusAction.GET_USER_PUB)
		  .append(more.getKey());
		
		String realSignature = MD5Util.MD5Upper32(sb.toString());
			
		if (realSignature.equals(status.getSign())) {
			fc.doFilter(request, response, status, more);
		} else {
			LoggerUtil.sys.info("无效的订单[{}:提-{} | 平-{}],签名无效",status.getClientCode(),status.getSign(),realSignature);
			fc.doCheckFaild(getStatusCode().getDwi().getInvalidSign());
		}
	}

}

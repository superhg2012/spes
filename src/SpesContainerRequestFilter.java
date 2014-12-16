import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.spes.constants.SessionConstants;

import com.alcatel.omc.fwk.utilities.trace.TraceManager;

/**
 * request filter enabled in web.xml servlet configuration
 * 
 * @author gbhe
 * 
 */
public class SpesContainerRequestFilter implements Filter {

	private static final ThreadLocal<Long> requestStartTime = new ThreadLocal<Long>();

	@Override
	public void destroy() {
      //call when container shut down
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//read filter params defined in web.xml
	}

	@Override
	public void doFilter(ServletRequest request_, ServletResponse response_,
			FilterChain filterChain) throws IOException, ServletException {
		// Save request start time
		requestStartTime.set(System.currentTimeMillis());
		
		HttpServletRequest request = (HttpServletRequest) request_;
		HttpServletResponse response = (HttpServletResponse) response_;

		HttpSession session = request.getSession();

		TraceManager.TrDebug("Request ==> " + request.getMethod() + " " + request.getRequestURL());
		TraceManager.TrDataflow("Request ==> " + request.getMethod() + " " + request.getRequestURL());

//		//Case1 : before login 
//		if (request.getRequestURI().indexOf("login.jsp") != -1) {
//			
//		} else {
//			if (session.getAttribute(SessionConstants.USER_NAME) == null) {
//				response.sendRedirect("login.jsp");
//				return;
//			}
//		}
		
		filterChain.doFilter(request_, response_);
		
		long duration = System.currentTimeMillis() - requestStartTime.get().longValue();
		long duration_h = duration / 3600000;
		long duration_mn = (duration - duration_h*3600000) / 60000;
		long duration_s = (duration - duration_h*3600000 - duration_mn*60000) / 1000;
		long duration_ms = duration - duration_h*3600000 - duration_mn*60000 - duration_s*1000;
		
		TraceManager.TrStatistics("Request "+request.getMethod()+" "+request.getRequestURL() +" "+duration+" "+duration_h+"h"+duration_mn+"mn"+duration_s+"s"+duration_ms+"ms");
		TraceManager.TrDebug("Request <== " + request.getMethod() + " " + request.getRequestURL());
		TraceManager.TrDataflow("Request <== " + request.getMethod() + " " + request.getRequestURL());
	}

}

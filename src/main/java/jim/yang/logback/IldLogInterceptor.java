package jim.yang.logback;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class IldLogInterceptor implements HandlerInterceptor {
	private static Logger b = LoggerFactory.getLogger(IldLogInterceptor.class);
	
	private String d = "";
	private String e = "Web";

	public IldLogInterceptor() {
	}

	public static final SimpleDateFormat simpleDateFmt = new SimpleDateFormat("yyyyMMddHHmmss");


	public String getLogdir() {
		return this.d;
	}

	public void setLogdir(String logdir) {
		this.d = logdir;
	}

	private String f = "INFO";

	public String getLogLevel() {
		return this.f;
	}

	public void setLogLevel(String logLevel) {
		this.f = logLevel;
	}
	
	private String a(HttpServletRequest request) {
		String s = request.getRequestURI();
		s = s.replaceFirst("/", "");
		int i = s.indexOf("/");
		s = s.substring(i);
		i = s.lastIndexOf(".");
		s = s.substring(0, i);
		s = s.replace("/", "_");
		return s;
	}
	
	MsgContext a(String path) {
		String date = simpleDateFmt.format(new Date());
		String logName = String.format("%s%s_%s", new Object[] { this.e, date, path });
		MsgContext ctx = new MsgContext(logName, this.e, System.currentTimeMillis());
		return ctx;
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String s = a(request);
		MsgContext cxt = a(s);
		b.info("{} 开始执行 请求URI {}", cxt.getMessageId(), request.getRequestURI().toString());
		MDC.put("processid", cxt.getMessageId());
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		MDC.remove("processid");
	}
}

package org.coody.framework.web;

import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.coody.framework.core.util.StringUtil;
import org.coody.framework.web.annotation.JsonOut;
import org.coody.framework.web.container.HttpContainer;
import org.coody.framework.web.container.MappingContainer;
import org.coody.framework.web.entity.MvcMapping;
import org.nico.noson.Noson;

@SuppressWarnings("serial")
public class DispatServlet extends HttpServlet{
	
	Logger logger=Logger.getLogger(DispatServlet.class);
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String path=request.getServletPath();
		logger.debug("收到请求 >>"+path);
		if(!MappingContainer.containsPath(path)){
			response.getWriter().print("page not found");
			response.setStatus(404);
			return;
		}
		MvcMapping mapping=MappingContainer.getMapping(path);
		//装载Request
		HttpContainer.setRequest(request);
		HttpContainer.setResponse(response);
		try {
			Object[] params=mapping.getParamsAdapt().doAdapt(mapping, request, response, request.getSession());
			Object	result=mapping.getMethod().invoke(mapping.getBean(), params);
			if(result==null){
				return;
			}
			JsonOut jsonSerialize=mapping.getMethod().getAnnotation(JsonOut.class);
			if(jsonSerialize!=null){
				response.setContentType("application/Json");
				String json=Noson.reversal(result);
				response.getWriter().print(json);
				return;
			}
			String viewFileName=StringUtil.toString(result);
			if(StringUtil.isNullOrEmpty(viewFileName)){
				response.getWriter().print("page not found");
				response.setStatus(404);
				return;
			}
			String viewPath=getServletConfig().getInitParameter("viewPath");
			String respFile=MessageFormat.format("{0}/{1}", viewPath,viewFileName);
			request.getRequestDispatcher(respFile).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void init(){}
}

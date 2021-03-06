package OuterInterfaces;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import Service.BlogVisitor2DBService;
import domain.TipMessage;
import domain.blog_visitor;

public class DeleteVisitor extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginID = request.getParameter("loginID");
		String visitorID = request.getParameter("visitorID");
		TipMessage msg = new TipMessage();
		ObjectMapper mapper = new ObjectMapper();
		if (visitorID == null){
			msg.setMessageCode("-100");
			msg.setMessageDetail("注册用户ID值未传送");
			response.getWriter().write(mapper.writeValueAsString(msg));
			return;
		}
		String temp = (String) this.getServletContext().getAttribute("loginID");
		if (temp == null){
			msg.setMessageCode("-200");
			msg.setMessageDetail("登录已过期或未登录");
			response.getWriter().write(mapper.writeValueAsString(msg));
			return;
		}
		if(!temp.equals(loginID)){
			msg.setMessageCode("-200");
			msg.setMessageDetail("登录已过期或未登录");
		}else{
			BlogVisitor2DBService service = new BlogVisitor2DBService();
			blog_visitor visitor = new blog_visitor();
			visitor.setVisitor_id(visitorID);
			if(service.deleteVisitor(visitor) >= 1){
				msg.setMessageCode("200");
				msg.setMessageDetail("用户删除成功");
			}else{
				msg.setMessageCode("-300");
				msg.setMessageDetail("用户删除失败");
			}
		}
		response.getWriter().write(mapper.writeValueAsString(msg));
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}

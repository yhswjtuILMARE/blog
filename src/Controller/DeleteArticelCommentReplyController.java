package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Service.BlogCommentReply2DBService;
import domain.blog_comment_reply;
import domain.blog_visitor;


public class DeleteArticelCommentReplyController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.getWriter().write("����δ��¼");
			return;
		}
		blog_visitor visitor = (blog_visitor) session.getAttribute("visitor");
		if (visitor == null) {
			response.getWriter().write("����δ��¼");
			return;
		}
		String commentReplyID = request.getParameter("commentReplyID");
		if (commentReplyID == null) {
			response.getWriter().write("��������");
			return;
		}
		BlogCommentReply2DBService service = new BlogCommentReply2DBService();
		blog_comment_reply comment = service.selectCommentReplyById(commentReplyID);
		if (!comment.getVisitor_id().equals(visitor.getVisitor_id())) {
			response.getWriter().write("ID����ɾ��ʧ��");
			return;
		}
		comment.setComment_reply_visibility(0);
		if(service.updateCommentReply(comment)< 1) {
			response.getWriter().write("ɾ��ʧ��");
		}else {
			response.getWriter().write("ɾ���ɹ�");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

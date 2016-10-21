package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CommentDao;
import entity.Comment;
import entity.Page;

public class TurnPageServlet extends HttpServlet {

	/**
	 * 
	 */
	CommentDao dao = new CommentDao();
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String st = request.getParameter("page_no");
		Page page = new Page();
		int page_no = 0;
		if (st != null) {
			page_no = Integer.parseInt(st);
			if (page_no <= -1) {
				page_no = 0;
			} else if (page_no >= page.getTotalPage()) {
				page_no = page.getTotalPage() - 1;
			}
		}

		List<Comment> list = null;
		page.setPage_no(page_no);
		list = dao.getPageComments(page);
		request.getSession().setAttribute("list", list);
		response.sendRedirect("ShowComments.jsp?page_no=" + (page_no));
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);

	}

}

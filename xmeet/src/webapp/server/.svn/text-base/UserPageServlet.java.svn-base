package webapp.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserPageServlet extends HttpServlet {

	private static final long serialVersionUID = 8526024028469162939L;
	
	public UserPageServlet() {
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String user = req.getRequestURI().replaceAll("/user/", "");
		
		//TODO remove developing url
		resp.sendRedirect("../Xmeet.html?gwt.codesvr=127.0.0.1:9997#!user;name=" + user);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}

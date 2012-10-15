package webapp.server;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 8526024028469162939L;
	
	private static final String DIR ="/images/user/";
	
	private String userid_;
 
	@Override
	@SuppressWarnings("rawtypes")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
	   
 
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory ();

		fileItemFactory.setSizeThreshold(5*1024*100); // 500kb
		fileItemFactory.setRepository(new File(getServletContext().getRealPath(DIR)));
 
		ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
		try {
			List items = uploadHandler.parseRequest(request);
			Iterator itr = items.iterator();
			while(itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				if (item.isFormField() && item.getFieldName().equals("userid")) {
					userid_ = item.getString();
				} else {
					if(userid_ != null) {
						File dir = new File(getServletContext().getRealPath(DIR + "/" + userid_));
						if (!dir.exists()) {
							if (!dir.mkdirs()) {
								throw new Exception("Can't create dir >" + dir.getPath() + "<");
							}
						}
						
						long millis = System.currentTimeMillis();
						File file = new File(dir.getPath() + "/" + millis + item.getName());
						file.createNewFile();
						item.write(file);
						out.println(millis + item.getName() + ";" + DIR + userid_ + "/");
					}
				}
			}
			out.close();
		}catch(FileUploadException ex) {
			log("Error encountered while parsing the request",ex);
		} catch(Exception ex) {
			log("Error encountered while uploading file",ex);
		}
 
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

}

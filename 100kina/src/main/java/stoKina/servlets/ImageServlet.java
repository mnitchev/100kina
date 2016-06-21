package stoKina.servlets;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		performTask(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		performTask(request, response);
	}
	private void performTask(HttpServletRequest request, HttpServletResponse response) throws ServletException,
				IOException {
		
		//TODO: understand how to pull single movie from database
		//OPTIONS:
		// -- new table with jpg names
		// -- other 
		
//		String imageFileName = "file.jpg";
//		String contextPath = getServletContext().getRealPath(File.separator);
//		File imgFile = new File(contextPath + imageFileName);
//		
//		response.setContentType("application/jpg");
//		response.addHeader("Content-Disposition", "attachment; filename=" + imageFileName);
//		response.setContentLength((int) imgFile.length());
		ByteArrayInputStream imageInputStream = new ByteArrayInputStream(new byte[20]); //TODO: change parameter
		OutputStream responseOutputStream = response.getOutputStream();
		int bytes;
		while ((bytes = imageInputStream.read()) != -1) {
			responseOutputStream.write(bytes);
		}
	}
}
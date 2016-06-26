package stoKina.servlets;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import stoKina.dao.MovieDAO;


@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {
	//not sure i can include this here. please check.
	MovieDAO movieDAO;
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
		
		// check if it's done properly
		String realPath =  getServletContext().getRealPath(File.separator);
		String imageTitle = realPath.substring(realPath.lastIndexOf("/"));
		response.setContentType("application/jpg");
		response.addHeader("Content-Disposition", "attachment; name=" + imageTitle);
		ByteArrayInputStream imageInputStream = new ByteArrayInputStream(movieDAO.getMovieByImageTitle(imageTitle).getImage());
		OutputStream responseOutputStream = response.getOutputStream();
		int bytes;
		while ((bytes = imageInputStream.read()) != -1) {
			responseOutputStream.write(bytes);
		}
	}
}
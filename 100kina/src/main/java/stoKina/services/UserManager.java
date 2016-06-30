package stoKina.services;

import java.net.HttpURLConnection;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import stoKina.dao.UserDAO;
import stoKina.model.User;

@Stateless
@Path("user")
public class UserManager {
	
	private static final Response RESPONSE_OK = Response.ok().build();
	
	@Inject
	private UserDAO userDAO;
	
	@Inject
	private UserContext context;
	
	@Path("secure")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerStaff(User user) {
		user.setRole(User.STAFF);
		if (userDAO.addUser(user)) {
			context.setCurrentUser(user);
			return RESPONSE_OK;
		}
		else {
			return Response.status(HttpURLConnection.HTTP_NOT_ACCEPTABLE).build();
		}
	}
	
	
	@Path("register")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerUser(User user) {
		user.setRole(User.USER);
		if (userDAO.addUser(user)) {
			context.setCurrentUser(user);
			return RESPONSE_OK;
		}
		else {
			return Response.status(HttpURLConnection.HTTP_NOT_ACCEPTABLE).build();
		}
	}
	
	@Path("login")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response validateUser(User user) {
		if(!userDAO.validateUserCredentials(user.getUserName(), user.getPassword())){
			return Response.status(HttpURLConnection.HTTP_UNAUTHORIZED).build();
		}
		user = userDAO.findByUserName(user.getUserName());
		context.setCurrentUser(user);
		return RESPONSE_OK;
	}
	
	@Path("authenticated")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	public Response isAuthenticated() {
		if(context.getCurrentUser() == null){
			return Response.status(HttpURLConnection.HTTP_NOT_FOUND).build();
		}
		return RESPONSE_OK;
	}
	
	@Path("current")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	public String getUser() {
		if(context.getCurrentUser() == null) {
			return null;
		}
		return context.getCurrentUser().getUserName();
	}
	
	@Path("logout")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	public void logoutUser() {
		context.setCurrentUser(null);
	}
	
}

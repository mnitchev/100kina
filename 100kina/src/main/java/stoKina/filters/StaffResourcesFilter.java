package stoKina.filters;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import stoKina.model.User;
import stoKina.services.UserContext;

@WebFilter({"/rest/ticket/staffGetAllTicketsForUser*", "/ticket-book.html"})
public class StaffResourcesFilter implements Filter {
	
	@Inject
	UserContext context;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if(!isHttpCall(request, response)){
			return;
		}
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		User currentUser = context.getCurrentUser();
		if(currentUser == null || !currentUser.getRole().equals(User.STAFF)){
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.html");
			return;
		}
		chain.doFilter(request, response);
	}
	
	private boolean isHttpCall(ServletRequest request, ServletResponse response) {
        return (request instanceof HttpServletRequest)
                && (response instanceof HttpServletResponse);
    }
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}

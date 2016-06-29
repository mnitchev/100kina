package stoKina.filters;

import java.io.IOException;
import java.text.Normalizer;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

public class SecurityFilter implements Filter {
	class XSSRequestWrapper extends HttpServletRequestWrapper {

		private Map<String, String[]> sanitizedQueryString;
		
		public XSSRequestWrapper(HttpServletRequest request) {
			super(request);
		}
		
		//QueryString overrides
		
		@Override
		public String getParameter(String name) {
			String parameter = null;
			String[] vals = getParameterMap().get(name); 
			
			if (vals != null && vals.length > 0) {
				parameter = vals[0];
			}
			
			return parameter;
		}

		@Override
		public String[] getParameterValues(String name) {
			return getParameterMap().get(name);
		}
		
		@Override
		public Enumeration<String> getParameterNames() {
			return Collections.enumeration(getParameterMap().keySet());
		}

		@SuppressWarnings("unchecked")
		@Override
		public Map<String,String[]> getParameterMap() {
			if(sanitizedQueryString == null) {
				Map<String, String[]> res = new HashMap<String, String[]>();
				Map<String, String[]> originalQueryString = super.getParameterMap();
				if(originalQueryString!=null) {
					for (String key : originalQueryString.keySet()) {
						String[] rawVals = originalQueryString.get(key);
						String[] snzVals = new String[rawVals.length];
						for (int i=0; i < rawVals.length; i++) {
							snzVals[i] = stripXSS(rawVals[i]);
							System.out.println("Sanitized: " + rawVals[i] + " to " + snzVals[i]);
						}
						res.put(stripXSS(key), snzVals);
					}
				}
				sanitizedQueryString = res;
			}
			return sanitizedQueryString;
		}

		
		private String stripXSS(String value) {
			String cleanValue = null;
			if (value != null) {
				cleanValue = Normalizer.normalize(value, Normalizer.Form.NFD);

				// Avoid null characters
				cleanValue = cleanValue.replaceAll("\0", "");
				
				// Avoid anything between script tags
				Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
		 
				// Avoid anything in a src='...' type of expression
				scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

				scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
				
				// Remove any lonesome </script> tag
				scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

				// Remove any lonesome <script ...> tag
				scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

				// Avoid eval(...) expressions
				scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
				
				// Avoid expression(...) expressions
				scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
				
				// Avoid javascript:... expressions
				scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
				
				// Avoid vbscript:... expressions
				scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
				
				// Avoid onload= expressions
				scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
				cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
			}
			return cleanValue;
		}
	}
	
	private boolean hasDirectoryTraversal(String value){
		String cleanValue = null;
		if(value != null){
			cleanValue = Normalizer.normalize(value, Normalizer.Form.NFD);
			
			Pattern directoryTraversalPattern = Pattern.compile("../", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			cleanValue = directoryTraversalPattern.matcher(cleanValue).replaceAll("");
			
			directoryTraversalPattern = Pattern.compile("%2e%2е/", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			cleanValue = directoryTraversalPattern.matcher(cleanValue).replaceAll("");
			

			directoryTraversalPattern = Pattern.compile("%2е%2е%2f", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			cleanValue = directoryTraversalPattern.matcher(cleanValue).replaceAll("");
			

			directoryTraversalPattern = Pattern.compile("..%2f", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			cleanValue = directoryTraversalPattern.matcher(cleanValue).replaceAll("");
			

			directoryTraversalPattern = Pattern.compile("%2е%2е%5c", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			cleanValue = directoryTraversalPattern.matcher(cleanValue).replaceAll("");
			

			directoryTraversalPattern = Pattern.compile("..%5c", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			cleanValue = directoryTraversalPattern.matcher(cleanValue).replaceAll("");
			

			directoryTraversalPattern = Pattern.compile("%252e%252e%255", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			cleanValue = directoryTraversalPattern.matcher(cleanValue).replaceAll("");

			directoryTraversalPattern = Pattern.compile("..%255", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			cleanValue = directoryTraversalPattern.matcher(cleanValue).replaceAll("");
			

			directoryTraversalPattern = Pattern.compile("%252e%252e/", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			cleanValue = directoryTraversalPattern.matcher(cleanValue).replaceAll("");
			
		}
		return value.equals(cleanValue);
	}
	
	private boolean isHttpCall(ServletRequest request, ServletResponse response) {
        return (request instanceof HttpServletRequest)
                && (response instanceof HttpServletResponse);
    }
	
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
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
		if(hasDirectoryTraversal(httpServletRequest.getRequestURI())){
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.html");
			return;
		}
		/*
		Map<String, String[]> requestParameters = httpServletRequest.getParameterMap();
		for (String parameterName : requestParameters.keySet()){
			String[] values = requestParameters.get(parameterName);
			for(int i = 0; i < values.length; i++){
				if(hasXSS(values[i])){
					return;
				}
				if(hasDirectoryTraversal(values[i])){
					return;
				}
			}
		}*/
		XSSRequestWrapper wrapper = new XSSRequestWrapper((HttpServletRequest)request);
		chain.doFilter(wrapper, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}

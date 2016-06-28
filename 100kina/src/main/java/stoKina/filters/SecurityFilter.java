package stoKina.filters;

import java.io.IOException;
import java.text.Normalizer;
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
import javax.servlet.http.HttpServletResponse;

public class SecurityFilter implements Filter {
	
	
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
	
	private boolean hasXSS(String value) {
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
			
			// Remove any  </script> tag
			scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
			cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

			// Remove any  <script ...> tag
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
			return;
		}
		
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
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}

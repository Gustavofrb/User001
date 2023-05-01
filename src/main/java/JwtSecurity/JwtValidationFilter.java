package JwtSecurity;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidationFilter extends BasicAuthenticationFilter{

	public static final String HEADER_ATRIBUTE = "Authorizathion";
	public static final String ATRIBUTE_PREFIX = "Bearer ";
	
	public JwtValidationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		// TODO Auto-generated constructor stub
	}
  @Override
protected void doFilterInternal(HttpServletRequest request, 
								HttpServletResponse response, 
								FilterChain filterChain) throws ServletException, IOException {
	  
	  String atribute = request.getHeader(HEADER_ATRIBUTE);
	  if (atribute == null){
		  filterChain.doFilter(request, response);
		  return;
	  }
	  
	  if(atribute.startsWith(ATRIBUTE_PREFIX)) {
		  filterChain.doFilter(request, response);
		  return;
	  }
	  String token = atribute.replace(ATRIBUTE_PREFIX, "");
	  UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);

	  SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	  filterChain.doFilter(request, response);
  }
  
  private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
	  String user = JWT.require(Algorithm.HMAC512(JwtAutenticathionFilter.TOKEN_SENHA))
			  .build()
			  .verify(token)
			  .getSubject();
	  if(user == null) {
		  return null;
	  }
	   return new UsernamePasswordAuthenticationToken(user,null, new ArrayList<>());
  }
}

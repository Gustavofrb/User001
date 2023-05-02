package JwtSecurity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.User.Api_Rest_User.User;
import com.User.Api_Rest_User_Details.UserDetail;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtAutenticathionFilter extends UsernamePasswordAuthenticationFilter {

	public static final int TOKEN_EXPIRAÇÃO = 600_000;
	public static final String TOKEN_SENHA = "463408a1-54c9-4307-bb1c-6cced559f5a7";
	
	private final AuthenticationManager authenticationmanager;

	public JwtAutenticathionFilter(AuthenticationManager authenticationmanager) {
		super();
		this.authenticationmanager = authenticationmanager;
	}
	
	public Authentication attemptAuthentication(HttpServletRequest request, 
												HttpServletResponse response) throws AuthenticationException {
		try {
            User user = new ObjectMapper()
                    .readValue(request.getInputStream(), User.class);

            return authenticationmanager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getLogin(),
                    user.getPassword(),
                    new ArrayList<>()
            ));

        } catch (IOException e) {
            throw new RuntimeException("Falha ao autenticar usuario", e);
        }
		
	
	}
	
	protected void successfulAuthentication(HttpServletRequest request,
	                                            HttpServletResponse response,
	                                            FilterChain chain,
	                                            Authentication authResult) throws IOException, ServletException {

	       UserDetail userdetail = (UserDetail) authResult.getPrincipal();
	       String token  = JWT.create().
           withSubject(userdetail.getUsername())
           .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRAÇÃO))
           .sign(Algorithm.HMAC512(TOKEN_SENHA));

	       response.getWriter().write(token);
	       response.getWriter().flush();
	}
}
package JwtSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.User.Api_Rest_User_Details.UserDetailServiceImpl;


@EnableWebSecurity
public class JwtConfiguration extends WebSecurityConfiguration {

	private final UserDetailServiceImpl userService;
	private final PasswordEncoder passwordEncoder;
	
	public JwtConfiguration(UserDetailServiceImpl userService, PasswordEncoder passwordencoder) {
		super();
		this.userService = userService;
		this.passwordEncoder = passwordencoder;
	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests()
		.requestMatchers(HttpMethod.POST,"/login").permitAll()
		.anyRequest().authenticated()
		.and()
		.addFilter(new JwtAutenticathionFilter(authenticationManager()))
        .addFilter(new JwtValidationFilter(authenticationManager()))
	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	private AuthenticationManager authenticationManager() {
		// TODO Auto-generated method stub
		return authenticationManager();
	}
	

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

	    CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
	    source.registerCorsConfiguration("/**", corsConfiguration);

	    return source;
	}


	

  
    }



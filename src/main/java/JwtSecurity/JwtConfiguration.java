package JwtSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.User.Api_Rest_User_Details.UserDetailServiceImpl;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.servlet.Filter;


@EnableWebSecurity
@Configuration
public class JwtConfiguration extends WebSecurityConfigurerAdapter {

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

	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.csrf().disable().authorizeRequests()
	                .antMatchers(HttpMethod.POST, "/login").permitAll()
	                .anyRequest().authenticated()
	                .and()
	                .addFilter((Filter) new JwtAutenticathionFilter(authenticationManager()))
	                .addFilter((Filter) new JwtValidationFilter(authenticationManager()))
	                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	 }
	 
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

	    CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
	    source.registerCorsConfiguration("/**", corsConfiguration);

	    return source;
	}


	

  
    }



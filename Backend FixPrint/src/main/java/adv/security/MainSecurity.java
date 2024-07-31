package adv.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import adv.security.jwt.JwtEntryPoint;
import adv.security.jwt.JwtTokenFilter;
import adv.security.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class MainSecurity{
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	JwtEntryPoint jwtEntryPoint;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	
	AuthenticationManager authenticationManager;
	
	 @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
	        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	        authenticationManager = builder.build();
	        http.authenticationManager(authenticationManager);

	        http.csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/auth/**",  
                		"/swagger-ui/**", 
                		"/swagger-resources/**", 
                		"/configuration/**",
                		"/v3/api-docs/swagger-config",
                		"/v3/api-docs/http",
                		"/roles/lista",
                		"/zona/lista",
                		"/incidencia/alta"
                		).permitAll()
                .anyRequest().authenticated()
            )
            .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtEntryPoint))
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
	        return http.build();
	    }

}

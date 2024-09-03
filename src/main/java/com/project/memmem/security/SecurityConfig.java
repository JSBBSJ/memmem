package com.project.memmem.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import lombok.RequiredArgsConstructor;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    //private final CustomUserDetailsService customUserDetailsService;
    private final MemmemLoginSuccessHandler LoginSuccessHandler;
    
    
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
	        .authorizeHttpRequests(authorize -> authorize
	        		.requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico","/bookBot/**","/bot/**","/topic/**","/message/**").permitAll()
	        		.requestMatchers("/","/groupSave","/login","/logout","/signup", "/login?error=true").permitAll()  // 로그인 페이지와 오류 페이지에 대한 접근 허용               
	                .requestMatchers("/mypage/","/reviews","/upload-temp").hasRole("USER")
	                .anyRequest().authenticated()
	            )
	        .formLogin(login -> login
	            .loginPage("/login")
	            .loginProcessingUrl("/login")
	            .usernameParameter("email")
	            .passwordParameter("password")
	            .successHandler(LoginSuccessHandler)
	            .failureUrl("/login?error=true") // 로그인 실패 시 리다이렉트할 URL
	            .permitAll()
	        )
	        .logout(logout -> logout
                .logoutRequestMatcher(
                    new OrRequestMatcher(
                        new AntPathRequestMatcher("/logout", "GET"),
                        new AntPathRequestMatcher("/logout", "POST")
                    )
                )
                .logoutUrl("/logout") // 기본 로그아웃 URL 설정
                .logoutSuccessUrl("/") // 로그아웃 성공 후 리다이렉트할 URL
                .invalidateHttpSession(true) // 세션 무효화
                .deleteCookies("JSESSIONID") // 쿠키 삭제
                .permitAll() // 로그아웃 URL 접근 허용
            );

        return http.build();
    }
}
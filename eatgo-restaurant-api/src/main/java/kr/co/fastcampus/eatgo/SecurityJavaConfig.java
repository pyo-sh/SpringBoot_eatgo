package kr.co.fastcampus.eatgo;

import kr.co.fastcampus.eatgo.filters.JwtAuthenticationFilter;
import kr.co.fastcampus.eatgo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {
    @Value("${jwt.secret}")
    private String secret;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        Filter filter = new JwtAuthenticationFilter(
                authenticationManager(), jwtUtil());

        http
                .cors().disable()
                .csrf().disable()
                .formLogin().disable()
                // h2 - 콘솔에 접속 가능하게 만듬... 보안에 중요하니 없앨 생각도
                .headers().frameOptions().disable()
                // http 에 있기 때문에 초기화를 하는 것
                .and()
                // 필터를 추가
                .addFilter(filter)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil(secret);
    }
}
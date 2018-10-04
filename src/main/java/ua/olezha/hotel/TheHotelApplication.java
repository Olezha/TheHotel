package ua.olezha.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class TheHotelApplication {

    public static void main(String[] args) {
        SpringApplication.run(TheHotelApplication.class, args);
    }
}

@Configuration
@EnableWebSecurity
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/management/**", "/h2-console/**").hasRole("HOTELIER")
                .anyRequest().permitAll()

                .and()
                .csrf()
                .ignoringAntMatchers("/h2-console/**")

                .and()
                .formLogin().permitAll()

                .and()
                .headers()
                .frameOptions()
                .disable();
    }
}

@Configuration
class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/ext/**")
                .addResourceLocations("/webjars/")
                .setCacheControl(CacheControl.maxAge(30L, TimeUnit.DAYS).cachePublic());
    }
}

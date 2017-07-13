package com.kedun.authmgr;


import com.kedun.authmgr.aop.CustomInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableRedisHttpSession
public class AuthmgrServiceApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(AuthmgrServiceApplication.class, args);
	}

	// 增加拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//这个RequestLog就是我们定义的拦截器
		registry.addInterceptor(new CustomInterceptor());
	}


}

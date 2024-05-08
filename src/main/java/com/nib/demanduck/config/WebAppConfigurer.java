package com.nib.demanduck.config;

import com.nib.demanduck.filter.ApiPrefixFilter;
import com.nib.demanduck.interceptor.UserLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/28 20:58
 */
@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Autowired
    private UserLoginInterceptor userLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginInterceptor)
                .addPathPatterns("/**")
                // 不需要登录的接口
                .excludePathPatterns(
                        "/user/login",
                        "/user/register",
                        "/user/sendValidCode",
                        "/file/upload",
                        "/term/association/**",
                        "*.html",
                        "*.css",
                        "*.js");
    }

    @Bean
    public FilterRegistrationBean<ApiPrefixFilter> apiPrefixFilter() {
        FilterRegistrationBean<ApiPrefixFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ApiPrefixFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}

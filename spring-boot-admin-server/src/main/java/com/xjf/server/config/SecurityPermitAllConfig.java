package com.xjf.server.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * 登录 Web 页面添加认证
 *
 * @author xjf
 * @date 2020/2/6 12:28
 */
@Configuration
public class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {

    private final String adminContextPath;

    public SecurityPermitAllConfig(AdminServerProperties adminServerProperties) {
        adminContextPath = adminServerProperties.getContextPath();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();

        successHandler.setTargetUrlParameter("redirectTo");
        // 静态资源和登录页面可以不用认证
        http.authorizeRequests().antMatchers(adminContextPath + "/asserts/**").permitAll()
                .antMatchers(adminContextPath + "/login").permitAll()
                // 其他请求必须认证
                .anyRequest().authenticated()
                // 自定义登录和退出
                .and().formLogin()
                .loginPage(adminContextPath + "/login").successHandler(successHandler)
                .and().logout()
                .logoutUrl(adminContextPath + "/logout")
                // 启用 HTTP-Basic， 用于 Spring Boot Admin Client 注册
                .and().httpBasic()
                .and().csrf().disable();
    }
}

package cn.zyk.pluton.portal.security;


import cn.zyk.pluton.portal.service.UserDetailServiceimpl;
import cn.zyk.pluton.portal.service.impl.FailHandleImpl;
import cn.zyk.pluton.portal.service.impl.SuccessHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailServiceimpl userDetailServiceimpl;
    @Autowired
    private SuccessHandlerImpl successHandler;
    @Autowired
    private FailHandleImpl failHandle;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailServiceimpl);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers(
                "/js/**", "/jiaoben4190/**", "/jiaoben7812/**", "/layui/**", "/vue/**", "/login.html",
                "/home.html","/register","/commodity/classifys","/banner","/commodity/popular","/commodity/classify/{id}",
                "/user/my","/bootstrap3/**","/shopping.html","/commodity/**","/details.html","/sdetails.html","/trolley/**","/comment/**","/code"
        ).permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login.html")
                .loginProcessingUrl("/login").successHandler(successHandler).failureHandler(failHandle).and().logout()
                .logoutUrl("/logout").logoutSuccessUrl("/login.html");
        http.headers().frameOptions().disable();
    }
}

package backend.pirocheck.config;

import backend.pirocheck.User.filter.SessionCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionCheckFilterConfig {

    @Bean
    public FilterRegistrationBean<SessionCheckFilter> sessionCheckFilter() {
        FilterRegistrationBean<SessionCheckFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SessionCheckFilter());
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}

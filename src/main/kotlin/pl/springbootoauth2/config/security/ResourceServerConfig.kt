package pl.springbootoauth2.config.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler

@Configuration
@EnableResourceServer
class ResourceServerConfig: ResourceServerConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        //-- define URL patterns to enable OAuth2 security
        http.anonymous().disable()
                .requestMatchers().antMatchers("/api/**")
                .and().authorizeRequests()
                .antMatchers("/api/**").access("hasRole('ADMIN') or hasRole('USER') and #oauth2.hasScope('read')")
                .and().exceptionHandling().accessDeniedHandler(OAuth2AccessDeniedHandler())
    }
}
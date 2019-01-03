package pl.springbootoauth2.config.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.core.io.ClassPathResource
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.approval.ApprovalStore
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory
import pl.springbootoauth2.config.security.userdetails.SpringBootUserDetailsService
import pl.springbootoauth2.domain.user.UserRepository

@Configuration
@EnableWebSecurity
class SecurityConfig(
        private val clientDetailsService: ClientDetailsService,
        private val springBootUserDetailsService: SpringBootUserDetailsService,
        private val userRepository: UserRepository,
        @Value("\${springBootService.admin.username}") private val adminUsername: String
) : WebSecurityConfigurerAdapter() {

    @Autowired
    @Throws(Exception::class)
    fun globalUserDetails(auth: AuthenticationManagerBuilder) {
        auth
                .userDetailsService(springBootUserDetailsService)
                .passwordEncoder(passwordEncoder())
    }

    // TODO CSRF protection - to implement !!
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests().antMatchers("/login").permitAll()
                .antMatchers("/signup").permitAll()
//                .antMatchers("/oauth/token/revokeById/**").permitAll()
//                .antMatchers("/tokens/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().permitAll()
                .and().csrf().disable()

//        http
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/about").permitAll()
//                .antMatchers("/signup").permitAll()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/oauth/token").permitAll()
//                .antMatchers("/oauth/authorize").permitAll()
//                .antMatchers("/oauth/confirm_access").permitAll()
//                .antMatchers("/actuator/**").permitAll() // TODO Remove after 'full OAUth2 implementation'!
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().permitAll()
//                .and()
//                .httpBasic()
//                .realmName("CRM_REALM")
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun tokenStore(): TokenStore {
        return JwtTokenStore(jwtTokenEnhancer())
    }

    @Bean
    protected fun jwtTokenEnhancer(): JwtAccessTokenConverter {
        val keyStoreKeyFactory = KeyStoreKeyFactory(
                ClassPathResource("springbootoauth2_jwt_key.jks"),
                userRepository.findByUsername(adminUsername).password.toCharArray())

        val converter = JwtAccessTokenConverter()
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("springbootoauth2"))

        return converter
    }

    @Bean
    @Autowired
    fun userApprovalHandler(tokenStore: TokenStore): TokenStoreUserApprovalHandler {
        val handler = TokenStoreUserApprovalHandler()
        handler.setTokenStore(tokenStore)
        handler.setRequestFactory(DefaultOAuth2RequestFactory(clientDetailsService))
        handler.setClientDetailsService(clientDetailsService)
        return handler
    }

    @Bean
    @Autowired
    @Throws(Exception::class)
    fun approvalStore(tokenStore: TokenStore): ApprovalStore {
        val store = TokenApprovalStore()
        store.setTokenStore(tokenStore)
        return store
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
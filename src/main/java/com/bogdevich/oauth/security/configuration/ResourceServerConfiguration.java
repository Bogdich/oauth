package com.bogdevich.oauth.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * Enable OAuth2 authentication on Spring.
 * {@link EnableResourceServer} enables a Spring Security filter
 * that authenticates request via incoming OAuth2 token.
 *
 * @author Eugene Bogdevich
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration  extends ResourceServerConfigurerAdapter{

    @Value("${security.oauth2.resource.id}")
    private String resourceId;

    private DefaultTokenServices defaultTokenServices;
    private TokenStore tokenStore;

    @Autowired
    public ResourceServerConfiguration(DefaultTokenServices defaultTokenServices, TokenStore tokenStore) {
        super();
        this.defaultTokenServices = defaultTokenServices;
        this.tokenStore = tokenStore;
    }

    /**
     * To allow the {@link ResourceServerConfigurerAdapter} to understand the token,
     * it must share the same characteristics with
     * {@link org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter}.
     *
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
                .resourceId(resourceId)
                .tokenServices(defaultTokenServices)
                .tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                //.requestMatcher(requestMatcher())
                .csrf().disable()
                .anonymous().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/api/hello").access("hasAnyRole('USER')")
                .antMatchers("/api/admin").hasRole("ADMIN")
                .antMatchers("/api/**").authenticated();
    }

    private static RequestMatcher requestMatcher() {
        return httpServletRequest -> {
            boolean match = false;
            String path = httpServletRequest.getServletPath();
            if (path.length() >= 5) {
                path = path.substring(0, 5);
                match = path.equals("/api/");
            }
            return match;
        };
    }

}

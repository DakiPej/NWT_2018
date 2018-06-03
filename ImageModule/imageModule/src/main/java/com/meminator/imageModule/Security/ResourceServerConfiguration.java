package com.meminator.imageModule.Security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Value("${security.oauth2.client.id}")
    private String clientID;

    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    @Value("${security.oauth2.resource.token-info-uri}")
    private String uri;

    @Bean
    @Primary
    public ResourceServerTokenServices tokenServices() {
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setClientId(this.clientID);
        tokenServices.setClientSecret(this.clientSecret);
        tokenServices.setCheckTokenEndpointUrl(this.uri);
        return tokenServices;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenServices(tokenServices()).resourceId("resource_id");
    }

    @Override
	public void configure(HttpSecurity http) throws Exception {
        http
			//.anonymous().disable()
			.anonymous().and()
			.authorizeRequests()
			.antMatchers("/user/register").anonymous()
			//.anyRequest().access("hasRole('user')")
			//.anyRequest().anonymous()
			.and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());

    }
    
}
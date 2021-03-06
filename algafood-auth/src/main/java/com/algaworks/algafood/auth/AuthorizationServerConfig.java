package com.algaworks.algafood.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private RedisConnectionFactory redisConnectionFactory;

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.inMemory()
              .withClient("algafood-web")
              .secret(passwordEncoder.encode("web123"))
              .authorizedGrantTypes("password", "refresh_token")
              .scopes("write", "read")
              .accessTokenValiditySeconds(60*60)
              .refreshTokenValiditySeconds(60*60)
            .and()
              .withClient("checktoken")
              .secret(passwordEncoder.encode("check123"))
            .and()
              .withClient("foodanalytics")
              .secret(passwordEncoder.encode(""))
              .authorizedGrantTypes("authorization_code")
              .scopes("write", "read")
              .redirectUris("http://localhost:5500")
            .and()
              .withClient("faturamento")
              .secret(passwordEncoder.encode("faturamento123"))
              .authorizedGrantTypes("client_credentials")
              .scopes("write", "read")
            .and()
              .withClient("webadmin")
              .authorizedGrantTypes("implicit")
              .scopes("write", "read")
              .redirectUris("http://aplicacao-cliente");
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security){
    security
            .checkTokenAccess("permitAll()")
            .allowFormAuthenticationForClients();
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    endpoints
            .authenticationManager(authenticationManager)
            .userDetailsService(userDetailsService)
            .reuseRefreshTokens(false)
            .tokenStore(redisTokenStore())
            .tokenGranter(tokenGranter(endpoints));
  }

  private TokenStore redisTokenStore() {
    return new RedisTokenStore(redisConnectionFactory);
  }

  private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
    var pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(endpoints.getTokenServices(),
            endpoints.getAuthorizationCodeServices(), endpoints.getClientDetailsService(),
            endpoints.getOAuth2RequestFactory());

    var granters = Arrays.asList(
            pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());

    return new CompositeTokenGranter(granters);
  }
}

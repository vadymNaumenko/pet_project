package de.pet_project.config;

import de.pet_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
//    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> {
                            auth.requestMatchers("/user", "/api/v1/auth/**", "/mvc/test/**", "/api/v1/auth/**", "/swagger-ui/**", "/v3/**").permitAll();
                            auth.requestMatchers("/api/v1/users/admin").hasAuthority("ADMIN");
                            auth.requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasAuthority("ADMIN");
                            auth.requestMatchers(HttpMethod.POST, "/api/game/users/**").hasAuthority("ADMIN");
                            auth.requestMatchers(HttpMethod.DELETE, "/api/game/users/**").hasAuthority("ADMIN");
                            auth.requestMatchers(HttpMethod.PUT, "/api/game/users/**").hasAuthority("ADMIN");

                            auth.requestMatchers(HttpMethod.POST, "/api/address/**").hasAuthority("ADMIN");
                            auth.requestMatchers(HttpMethod.DELETE, "/api/address/**").hasAuthority("ADMIN");
                            auth.requestMatchers(HttpMethod.PUT, "/api/address/**").hasAuthority("ADMIN");

//                                .requestMatchers("/user/**","**/game/**").hasAnyRole("USER", "ADMIN")
                            auth.anyRequest().permitAll();
                        }
                )
                .authenticationProvider(authenticationProvider)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                .oauth2Login(Customizer.withDefaults())

//                .oauth2Login(config-> config
//                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(oidcUserService())))
                .build();
    }

//    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
//        return userRequest -> {
//            String email = userRequest.getIdToken().getClaims().get("email").toString();
//            //todo create user userService.create
//            UserDetails userDetails = userService.loadUserByUsername(email);
////            new OidcUserService().loadUser()
//            DefaultOidcUser oidcUser = new DefaultOidcUser(userDetails.getAuthorities(), userRequest.getIdToken());
//            Set<Method> userDetailsMethods = Set.of(UserDetails.class.getMethods());
//            return (OidcUser) Proxy.newProxyInstance(SecurityConfiguration.class.getClassLoader(), new Class[]{UserDetails.class, OidcUser.class},
//                    (proxy, method, args) -> userDetailsMethods.contains(method)
//                            ? method.invoke(userDetails, args)
//                            : method.invoke(oidcUser, args));
//        };
//    }

}

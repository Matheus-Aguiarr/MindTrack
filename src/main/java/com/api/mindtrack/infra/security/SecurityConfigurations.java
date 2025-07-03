package com.api.mindtrack.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// @Configuration: tells the spring that this class is for configuration
// @EnableWebSecurity: tells to spring to enable the WebSecurity Configuration, and I'll configure inside this class.
@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

//    Instanced for add this filter manually to the filterChain
    @Autowired
    private SecurityFilter securityFilter;



//  @Bean: is necessary to Spring recognize and use our SecurityConfiguration.
    @Bean
/*
     SecurityFilterChain => Configure the filter chain (cadeia de filtros) of the application.
     One application can have unlimited filters, and this method will configure how they're gonna work.

     HttpSecurity => permits configure what filters, authentications and protections are gonna be applied on the HTTP requests.
     CSRF => Cross-Site Request Forgery. Why disable? -> Because CSRF is relevant in web applications session cookies based.
       - In REST APIs that uses JWT or any other STATELESS authentication, doesn't have any sense use CSRF, therefore, usually we disable.

     sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS) => Disable the Session Creation on the server
       - this means that the API is StateLess:
            - The server don't store the state of the user through the requests
            - Every request need to include all the necessary information to the authentication, usually, a JWT Token at the header of the request.
       - without that, Spring would create a session in the backend, and that makes no sense in APIs that uses JWT.

    authorizeHttpRequests => Here, i can configure how's gonna be the authorization of my requests
            - Receive an "authorize" and then, configure.
            - requestMatchers(HttpMethod.POST, "/product").hasRole("ADMIN"): The request that matches with...
                Type of the request (HttpMethod): POST,
                Endpoint of the request (pattern): "/product"
            - To do this request, the user has to be...
                hasRole("ADMIN")

            - anyRequest().authenticated()
                To any other request, the user only has to be authenticated.



*/
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .anyRequest().authenticated()
                ).addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

/*
    What this method do?
    This method creates a bean of AuthenticationManager, what's a Spring component responsible for executes the authentication (validates user and password).
        Without this bean, you'd not could inject the AuthenticationManager in the Controller, like you did. (@AutoWired private AuthenticationManager[...])
    @Bean => Says to spring that this method creates a bean, an object that Spring manage and can be injected in other places.
    The method receive as a parameter a instance of AuthenticationConfiguration, that's a class of Spring.
        - This class serves for access the internals configurations of Spring Security, including the AuthenticationManager.

    return authenticationConfiguration.getAuthenticationManager(); here, you're saying =>
        "Spring, give me the AuthenticationManager, that you've already configured internally"
        Spring by itself create this AuthenticationManager, using the UserDetailsService and PasswordEncoder configurations.
        - This makes Spring deliver the AuthenticationManager with all the configuration correct, including =>

*/
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

//  This method will basically Encode the password using BCryptPasswordEncoder (method of hashing). Spring will use this automatically in the AuthenticationManager.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package com.api.auth.infra.security;


import com.api.auth.domain.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//  This class will intercept all the HTTP requests.

/*
    @Component: marks the class as a component of spring.
    It extends OncePerRequestFilter, what means that this filter will be executed Once Per HTTP Request (uma vez por requisicao).
*/
@Component
public class SecurityFilter extends OncePerRequestFilter {


//  The service who knows how to generate and validate JWT tokens.
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

/*
    This method is called automatically in every HTTP request in the API.
    Here is where implements the logic for:
        - Verify if there's some token
        - Validate the token
        - Authenticate the user in the security context

    var tokenJWT = this.recoverToken(request); => Try to recover the token from header Authorization (o header do postman)

    if (tokenJWT != null) { => if there's some token.
    var subject = tokenService.validateToken(tokenJWT); => validate the token, if valid, it will return the subject (user login)

    UserDetails user = userRepository.findByLogin(subject); => Find the user by his login.
        - Important to take the details like the Roles (getAuthorities())

    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()); => Creates an authentication in Spring Security
    SecurityContextHolder.getContext().setAuthentication(authentication);
        - Is creating an valid authentication object, where:
            - User -> is the authenticated user
            - null -> it would be the password field, but since the token was already validated, doesn't need the password here.
            - user.getAuthorities() -> Are the roles/permissions of the user.

    filterChain.doFilter(request, response); => Calls the next filter of the chain (O proximo filtro da cadeia)

*/
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = this.recoverToken(request);
        if (tokenJWT != null) {
            var subject = tokenService.validateToken(tokenJWT);
            UserDetails user = userRepository.findByLogin(subject);


            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

/*
    Recover the token from the Header.
    Receives a HttpServletRequest, this is the request by itself.
    var authHeader = request.getHeader("Authorization"); => Search for the Authorization Header (where the client put the TokenJWT)

    if the authHeader isn't null, return the authHeader replacing "Bearer " for nothing, to return only the tokenJWT.
*/
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if(authHeader != null) {
            return authHeader.replace("Bearer ", "");
        }
        return null;
    }
}



package com.api.auth.controller;

import com.api.auth.domain.user.UserModel;
import com.api.auth.domain.user.UserRole;
import com.api.auth.domain.user.repository.UserRepository;
import com.api.auth.dto.RegisterUserDTO;
import com.api.auth.dto.TokenJwtDTO;
import com.api.auth.dto.UserAuthDTO;
import com.api.auth.infra.security.TokenService;
import jakarta.persistence.EnumType;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
//    Component of spring that makes the authentication. (Validates if login and password are correct.
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

//    Request: POST /auth/login
    @PostMapping("/login")
/*
    What this method do? => Creates an endpoint /auth/login that receives login and password, authenticates in Spring Security and,
        if it's right, return 200OK.

    var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password()); =>
        Create an object UsernamePasswordAuthenticationToken that is a default object of Spring Security that loads the credentials.

    var auth = this.authenticationManager.authenticate(usernamePassword); =>
        Says to Spring -> Look, there's my UsernamePasswordAuthenticationToken, now, try to authenticate.
        It calls the method "loadByUserName()" from the UserModel class
        Search the user in the db, compare the received Password with the password saved on the db.
            If correct -> return an object Auhtentication with user and permissions.
            If wrong -> throws Exception, usually "BadCredentialsException"
*/
    public ResponseEntity login(@RequestBody @Valid UserAuthDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
//        ToDo: finish explaining this class using comments.

        var token = tokenService.generateToken((UserModel) auth.getPrincipal());

        return ResponseEntity.ok(new TokenJwtDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterUserDTO data) {
//        If exists someone with this Login...
        if(this.userRepository.findByLogin(data.login()) != null) {
            return ResponseEntity.badRequest().build();
        }
//        Encode the password
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        UserModel newUser = new UserModel(data.login(), encryptedPassword, data.role());
        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}

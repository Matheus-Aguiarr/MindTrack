package com.api.mindtrack.controller;

import com.api.mindtrack.domain.user.UserModel;
import com.api.mindtrack.domain.user.dto.UserResponseDTO;
import com.api.mindtrack.domain.user.repository.UserRepository;
import com.api.mindtrack.domain.user.dto.RegisterUserDTO;
import com.api.mindtrack.domain.user.dto.TokenJwtDTO;
import com.api.mindtrack.domain.user.dto.UserAuthDTO;
import com.api.mindtrack.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserAuthDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

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

    @GetMapping("/user/profile")
    public ResponseEntity<UserResponseDTO> getUserProfile() {
//        Get the authenticatedUser
        var authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        var user = (UserModel) authentication.getPrincipal();
        return ResponseEntity.ok(new UserResponseDTO(user.getLogin(), user.getRole().toString()));
    }
}

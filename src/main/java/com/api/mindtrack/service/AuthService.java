package com.api.mindtrack.service;

import com.api.mindtrack.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


/*
    This method is the way spring security is gonna consult the user when try to login, and then verify ->
      -  if the user exist.
      -  if the password is correct.
      -  if the user is activate.
      -  what roles/authorities the user has (it's gonna verify by the method getAuthorities in the UserModel).

    if ALL OK => generate the Token JWT (if it's using JWT) or create the session (if it's a stateful app (In this case, it's StateLESS)).
    After that => in the next protected request, spring is NOT gonna call this method, it will just verify the Token JWT and the roles.

    In short => the method loadUserByUsername is responsible for load the data of the user (login, password, roles),
   to spring then do the process of login correctly.
*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username);
    }
}

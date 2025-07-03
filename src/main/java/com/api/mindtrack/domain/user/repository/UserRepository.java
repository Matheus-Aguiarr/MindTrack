package com.api.mindtrack.domain.user.repository;

import com.api.mindtrack.domain.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<UserModel, String> {
//    Is important that the method findByLogin return always UserDetails, bc it's gonna be used of spring security later;
    UserDetails findByLogin(String login);
}

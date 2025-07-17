package com.api.mindtrack.domain.user;

import com.api.mindtrack.domain.folder.FolderModel;
import com.api.mindtrack.domain.subject.SubjectModel;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "users")
@Table(name = "users")
public class UserModel implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user")
    private List<SubjectModel> subject;

    @OneToMany(mappedBy = "user")
    private List<FolderModel> folders;

    public UserModel(Long id, String login, String password, UserRole role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public UserModel(String login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public UserModel() {}


    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public UserRole getRole() {
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//      Verify if the attribute role of this user is equals to ADMIN.
        if(this.role == UserRole.ADMIN) {
//          If the attribute is admin -> return a List with two permissions (the permissions of the admin and all the permissions of a common user)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER")); //Esse prefico de "ROLE_" é pra conseguir fazer uma config. do Spring depois, por que ele espera o prefixo ROLE pra conseguir identificar com o metodo "hasRole()" dele
        } else {
//          If the attribute is not ADMIN, it is USER and they have only permission of a common user.
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    public List<SubjectModel> getSubject() {
        return subject;
    }

    public List<FolderModel> getFolders() {
        return folders;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

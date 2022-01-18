package com.example.MonManAppServer_spring.security.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;


/*

Это entity-class для взаимодействия с базой данных. Помимо этого в нём есть некоторая бизнеслогика

У каждого user есть свой id, username, password, passwordConfirm, множество ролей

Класс user исплементирует интерфейс UserDetails

 */

@Entity
@Table(name = "t_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }


    private String username;
    public void setUsername(String username) { this.username = username; }


    private String password;
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }


    @Transient
    private String passwordConfirm;
    public String getPasswordConfirm() { return passwordConfirm; }
    public void setPasswordConfirm(String passwordConfirm) { this.passwordConfirm = passwordConfirm; }


    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }


    @Override
    public String getUsername() {
        return username;
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

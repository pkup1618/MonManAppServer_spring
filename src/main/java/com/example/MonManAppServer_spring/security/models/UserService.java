package com.example.MonManAppServer_spring.security.models;

import com.example.MonManAppServer_spring.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//Чем отличается @Component от @Service
@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username);
    }
}

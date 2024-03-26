package com.example.OnlineBookStore.service;

import com.example.OnlineBookStore.model.MyUser;
import com.example.OnlineBookStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = repository.findByUsername(username).orElse(null);

        if (myUser == null) {
            throw new UsernameNotFoundException("User not found");
        }
        List<SimpleGrantedAuthority> authorities;

        if (myUser.getIsAdmin()) {
            authorities = Arrays.asList(new SimpleGrantedAuthority("admin"));
        } else {
            authorities = Arrays.asList(new SimpleGrantedAuthority("user"));
        }

        return new User(myUser.getUsername(), myUser.getPassword(), authorities);
    }
}
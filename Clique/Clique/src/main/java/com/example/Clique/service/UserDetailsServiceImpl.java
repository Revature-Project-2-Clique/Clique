package com.example.Clique.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Clique.Entities.Users;
import com.example.Clique.repository.UsersRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users appUser = usersRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return User.builder()
                    .username(appUser.getUsername())
                    .password(appUser.getPassword())
                    .roles("USER")
                    .build();
    }

    public Long getUserIdByUsername(String username) throws UsernameNotFoundException {
        Users appUser = usersRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return appUser.getUser_id();
    }
}

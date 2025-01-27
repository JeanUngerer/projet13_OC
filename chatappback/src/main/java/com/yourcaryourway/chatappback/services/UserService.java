package com.yourcaryourway.chatappback.services;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.yourcaryourway.chatappback.entities.User;
import com.yourcaryourway.chatappback.exceptions.BadRequestExceptionHandler;
import com.yourcaryourway.chatappback.exceptions.NotFoundExceptionHandler;
import com.yourcaryourway.chatappback.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    public User findUserByEmail(String email){
           User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundExceptionHandler("User not found"));
        return user;
    }

    public User create(User user) {
        try {
            user.setId(null);
            user = userRepository.save(user);
            return user;
        } catch (Exception e) {
            System.out.println("\n\n\nError : " + e.getMessage());
            throw new BadRequestExceptionHandler("Failed to create user");
        }
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findUserByEmail(email);
        List<String> roles = new ArrayList<>();
        roles.add("user");
        List<SimpleGrantedAuthority> authorities = roles
                .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}

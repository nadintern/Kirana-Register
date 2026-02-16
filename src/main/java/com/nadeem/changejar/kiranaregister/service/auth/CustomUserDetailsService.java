package com.nadeem.changejar.kiranaregister.service.auth;

import com.nadeem.changejar.kiranaregister.entity.Users;
import com.nadeem.changejar.kiranaregister.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Users user = userRepository.findById(new ObjectId(userId))
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userId));

        return new User(
                user.getId().toHexString(),
                user.getPasswordHash(),
                user.isActive(),
                true, true, true,
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList())
        );
    }
}

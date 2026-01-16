package com.checkit.checkit_backend.service;

import com.checkit.checkit_backend.dto.UserDTO;
import com.checkit.checkit_backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService {
    private final UserRepository repository;

    public UserDetailsService(UserRepository repository) {
        this.repository = repository;
    }


    public UserDTO loadUserByUsername(String email) throws UsernameNotFoundException {
        // Since the User entity implements UserDetails, we can return it directly.
        var user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + email));

        return new UserDTO(user.getUsername(),user.getPassword(),user.getEmail());
    }
}

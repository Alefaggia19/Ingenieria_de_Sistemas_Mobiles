package com.checkit.checkit_backend.service;

import com.checkit.checkit_backend.dto.UserDTO;
import com.checkit.checkit_backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    public UserDetailsService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }


    public UserDTO loadUserByUsername(String email) throws UsernameNotFoundException {
        // Since the User entity implements UserDetails, we can return it directly.
        var user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + email));

        return new UserDTO(user.getRealname(),user.getEmail());
    }

    public void changeUserDetails(String email,String realName,String password){
        var user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + email));
        user.setRealname(realName);
        user.setPassword(passwordEncoder.encode(password));

    }
}

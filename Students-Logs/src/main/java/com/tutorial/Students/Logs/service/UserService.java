package com.tutorial.Students.Logs.service;

import com.tutorial.Students.Logs.entity.AppUser;
import com.tutorial.Students.Logs.entity.Role;
import com.tutorial.Students.Logs.repository.RoleRepository;
import com.tutorial.Students.Logs.repository.UserRepository;
import java.util.HashSet;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Role createRole(String name) {
        if (roleRepository.existsByName(name)) {
            return roleRepository.findByName(name).orElseThrow();
        }
        Role role = new Role(null, name);
        return roleRepository.save(role);
    }

    public AppUser registerUser(String username, String rawPassword, Set<String> roleNames) {
        if (userRepository.existsByUsername(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        Set<Role> roles = new HashSet<>();
        if (roleNames == null || roleNames.isEmpty()) {
            roles.add(getOrCreateRole("USER"));
        } else {
            for (String roleName : roleNames) {
                roles.add(getOrCreateRole(roleName));
            }
        }

        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRoles(roles);
        return userRepository.save(user);
    }

    private Role getOrCreateRole(String name) {
        return roleRepository.findByName(name)
                .orElseGet(() -> roleRepository.save(new Role(null, name)));
    }
}

package com.healthy.style.security;

import com.healthy.style.entity.Role;
import com.healthy.style.entity.User;
import com.healthy.style.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return AuthorityUtils.createAuthorityList(user.getRoles().stream()
                .map(Role::getName)
                .toArray(String[]::new));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Username " + username + " not found!"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                getAuthorities(user));
    }

}

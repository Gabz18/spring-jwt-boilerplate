package com.gabz18.jwtboilerplate.security;

import com.gabz18.jwtboilerplate.model.AppUser;
import com.gabz18.jwtboilerplate.repository.AppUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private AppUserRepository appUserRepository;

    public UserDetailsServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<AppUser> appUser = appUserRepository.findByUsername(s);
        if (!appUser.isPresent()) {
            throw new UsernameNotFoundException(s);
        }
        return new User(appUser.get().getUsername(), appUser.get().getPassword(), Collections.emptyList());
    }
}

package com.gabz18.jwtboilerplate.service;

import com.gabz18.jwtboilerplate.model.AppUser;
import com.gabz18.jwtboilerplate.repository.AppUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Optional;

@Service
public class AppUserService {

    private AppUserRepository appUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AppUserService(AppUserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        appUserRepository = userRepository;
        bCryptPasswordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> requestBody) {
        Optional<String> missingParameter = checkRequestParameters(requestBody);
        if (missingParameter.isPresent()) {
            return new ResponseEntity<>("Parameter \""
                    + missingParameter.get()
                    + "\" is required", HttpStatus.BAD_REQUEST);
        }
        String username = requestBody.get("username");
        Optional<AppUser> optionalAppUser = appUserRepository.findByUsername(username);
        if (optionalAppUser.isPresent()) {
            return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
        }
        appUserRepository.save(new AppUser(username, bCryptPasswordEncoder.encode(requestBody.get("password"))));
        return new ResponseEntity<>("User registered", HttpStatus.OK);
    }

    private Optional<String> checkRequestParameters(Map<String, String> requestBody) {
        if (!requestBody.containsKey("username")) {
            return Optional.of("username");
        }
        if (!requestBody.containsKey("password")) {
            return Optional.of("password");
        }
        return Optional.empty();
    }
}

package com.movie.moviebookingwebsiteapis.service;


import com.movie.moviebookingwebsiteapis.entity.User;
import com.movie.moviebookingwebsiteapis.repository.UserRepository;
import com.movie.moviebookingwebsiteapis.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(MyUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("User not Found"));
    }
}

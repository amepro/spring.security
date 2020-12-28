package com.pxp.spring.security.jwt.services;

import com.pxp.spring.security.jwt.entity.UserEntity;
import com.pxp.spring.security.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PXPUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findById(s);

        if(user.isPresent()){
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            Arrays.asList(user.get().getRoles().split(",")).stream().forEach(authority ->{
                authorities.add(new SimpleGrantedAuthority(authority));
            });
            return new User(user.get().getUserName(), user.get().getPassword(), authorities);
        }else {
            throw new UsernameNotFoundException("User " + s + " does not exist...");
        }
    }
}

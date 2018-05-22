package com.jwt.study.security_jwt.config;

import com.jwt.study.security_jwt.entity.MyUser;
import com.jwt.study.security_jwt.reponsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailServiceImpl implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = userRepository.findByUsername(username);
        if(user ==null){
            throw new UsernameNotFoundException(username);
        }
        return new User(user.getUsername(),user.getPassword(),emptyList());
    }
}

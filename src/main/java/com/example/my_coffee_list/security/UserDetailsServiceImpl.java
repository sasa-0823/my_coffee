package com.example.my_coffee_list.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.postingapp.entity.User;
import com.example.postingapp.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsServiceImpl {
    private final UserRepository userRepository;

@Autowired
public UserDetailsServiceImpl(UserRepository userRepository){
    this.userRepository = userRepository;
}

@Override
public UserDetails loadUserByUsername(string email)throws UsernameNotFoundException{
    try {
        User user = userRepository.findByEmail(Stirng email);
        return new UserDetailsImpl(User);
    }catch{
        throw new UsernameNotFoundException("ユーザーが見つかりませんでした。");
    }
}

}

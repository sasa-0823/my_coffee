package com.example.my_coffee_list.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.my_coffee_list.entity.User;
import com.example.my_coffee_list.repository.UserRepository;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final UserRepository userRepository;

public UserDetailsServiceImpl(UserRepository userRepository){
    this.userRepository = userRepository;
}

@Override
public UserDetails loadUserByUsername(String email)throws UsernameNotFoundException{
    try {
        User user = userRepository.findByEmail(email);
        return new UserDetailsImpl(user);
    }catch(Exception e){
        System.out.println("エラー");
        throw new UsernameNotFoundException("ユーザーが見つかりませんでした。");
    }
}

}

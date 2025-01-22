package com.example.my_coffee_list.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.my_coffee_list.Form.SignupForm;
import com.example.my_coffee_list.entity.User;
import com.example.my_coffee_list.repository.UserRepository;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional  //トランザクション化
  public void createUser(SignupForm signupForm){
    User user = new User();

    user.setName(signupForm.getName());
    user.setEmail(signupForm.getEmail());
    user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
    user.setEnabled(true);

    userRepository.save(user);

    User registUser = userRepository.findByEmail(signupForm.getEmail());
    user.setImg(registUser.getId() + ".jpg");

    userRepository.save(user);
  }

  public boolean isEmailRegistered(String email){
    User user = userRepository.findByEmail(email);
    return user != null; //nullの場合false
  }

  public  boolean isSamePassword(String password, String passwordConfimation){
    return password.equals(passwordConfimation);
  }
}

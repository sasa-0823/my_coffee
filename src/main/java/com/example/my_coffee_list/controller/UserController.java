package com.example.my_coffee_list.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.my_coffee_list.entity.User;
import com.example.my_coffee_list.security.UserDetailsImpl;
import com.example.my_coffee_list.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }


  // ユーザー名変更
  @GetMapping("/changeName")
  public String changeName(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
      @RequestParam("editName") String name, HttpServletRequest request) {
    String editName = name;
    String resUrl = request.getHeader("Referer");

    if (userDetailsImpl.getUsername() != editName) {
      User user = userDetailsImpl.getUser();
      user.setName(editName);
      userService.changeName(user); //ユーザー情報書き換え
    }
    
    return "redirect:" + resUrl;

  }
}

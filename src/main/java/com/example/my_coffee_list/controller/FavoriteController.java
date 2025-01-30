package com.example.my_coffee_list.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.my_coffee_list.security.UserDetailsImpl;
import com.example.my_coffee_list.service.FavoriteService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class FavoriteController {
  private final FavoriteService favoriteService;


  public FavoriteController(FavoriteService favoriteService) {
    this.favoriteService = favoriteService;
  }

  // お気に入り登録 <--> お気に入り削除
  @PostMapping("/Favorite/{recipeId}")
  public String favToggle(@PathVariable("recipeId") Integer recipeId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, HttpServletRequest request) {

    Integer userId = userDetailsImpl.getUser().getId();
    favoriteService.favToggle(recipeId, userId);

    //元の画面に遷移
    String resUrl = request.getHeader("Referer");
    System.out.println(resUrl);
    return "redirect:" + resUrl;
  }

}

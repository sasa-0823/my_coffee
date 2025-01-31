package com.example.my_coffee_list.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.my_coffee_list.security.UserDetailsImpl;
import com.example.my_coffee_list.service.FavoriteService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
// @Controller
public class FavoriteController {
  private final FavoriteService favoriteService;


  public FavoriteController(FavoriteService favoriteService) {
    this.favoriteService = favoriteService;
  }

  // お気に入り登録 <--> お気に入り削除
  // @PostMapping("/Favorite/{recipeId}")
  // public String favToggle(@PathVariable("recipeId") Integer recipeId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, HttpServletRequest request) {

  //   Integer userId = userDetailsImpl.getUser().getId();
  //   favoriteService.favToggle(recipeId, userId);

  //   //元の画面に遷移
  //   String resUrl = request.getHeader("Referer");
  //   System.out.println(resUrl);
  //   return "redirect:" + resUrl;
  // }

  @GetMapping("/Favorite/{recipeId}")
  public ResponseEntity<Map<String, Boolean>> favInfoToView(@PathVariable("recipeId") Integer recipeId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, HttpServletRequest request) {

    Integer userId = userDetailsImpl.getUser().getId();

    // お気に入り登録 <--> お気に入り削除
    favoriteService.favToggle(recipeId, userId);

    // viewにお気に入り状況を返す(fav:true / notfav:false)
    boolean fovEnable = favoriteService.checkFavforUser(recipeId, userId);
    
    //キーを付けてjson形式に
      return ResponseEntity.ok()
      .contentType(MediaType.APPLICATION_JSON)
      .body(Map.of("isFav", fovEnable));
    
  }

    

}

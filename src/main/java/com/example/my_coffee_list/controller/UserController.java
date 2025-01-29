package com.example.my_coffee_list.controller;

import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.my_coffee_list.entity.User;
import com.example.my_coffee_list.security.UserDetailsImpl;
import com.example.my_coffee_list.service.CommentService;
import com.example.my_coffee_list.service.FavoriteService;
import com.example.my_coffee_list.service.RecipeService;
import com.example.my_coffee_list.service.UserService;
import com.example.my_coffee_list.service.VerificationTokenService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {
  private final UserService userService;
  private final RecipeService recipeService;
  private final CommentService commentService;
  private final FavoriteService favoriteService;
  private final VerificationTokenService verificationTokenService;

  public UserController(UserService userService, RecipeService recipeService, CommentService commentService,
      FavoriteService favoriteService, VerificationTokenService verificationTokenService) {
    this.userService = userService;
    this.recipeService = recipeService;
    this.commentService = commentService;
    this.favoriteService = favoriteService;
    this.verificationTokenService = verificationTokenService;
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
      userService.changeUser(user); //ユーザー情報書き換え
    }
    
    return "redirect:" + resUrl;

  }

  // ユーザー削除
  @GetMapping("/deleteAccount")
    public String deleteAccount(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
      User user = userDetailsImpl.getUser();
      //ユーザーのコンテンツを削除(外部キー制約の為)
      commentService.deleteUserComment(user);
      favoriteService.deleteUserFavorite(user);
      verificationTokenService.deleteUserToken(user);
      recipeService.deleteUsersRecipe(user);

      // ユーザーの認証を有効から無効に変更
      // 再登録出来るようにメールアドレスを変更(メールアドレスはユニークな為)
      user.setEnabled(false);
      UUID uuid = UUID.randomUUID();
      String lockString = uuid.toString();
      user.setEmail(lockString);
      user.setImg(lockString);
      userService.changeUser(user);  //メールアドレスとユーザアイコンの名前をランダム値に変更
      

      return "redirect:/login";
      
    }
  
  
}

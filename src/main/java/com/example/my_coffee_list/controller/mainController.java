package com.example.my_coffee_list.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.my_coffee_list.entity.Bean;
import com.example.my_coffee_list.entity.Recipe;
import com.example.my_coffee_list.entity.User;
import com.example.my_coffee_list.security.UserDetailsImpl;
import com.example.my_coffee_list.service.BeanService;
import com.example.my_coffee_list.service.RecipeService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class mainController {
  private RecipeService recipeService;
  private BeanService beanService;

  public mainController(RecipeService recipeService, BeanService beanService) {
    this.recipeService = recipeService;
    this.beanService = beanService;
  }

  // ホーム画面
  @GetMapping("/")
  public String indexPage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
    
    User user = userDetailsImpl.getUser();
    
    // ログインしていない場合の処理->ログインフォームに帰る
    if (user == null) {
      model.addAttribute("errorMessage", "アカウントが存在しません");
      return "redirect:/login";
    }

    // レシピ作成ユーザーとログインユーザーが同じがチェック
    // key:checkUser ---> true / false
    recipeService.checkRecipeSameUser(model, user, userDetailsImpl);

    List<Recipe> RecipeList = recipeService.recipeByUser(user);

    model.addAttribute("user", user);
    model.addAttribute("recipeList", RecipeList);

    return "index";
  }

  // ヘッダーの豆検索フォーム→検索結果へ
  @GetMapping("/search")
  public String RecipeSearch(@RequestParam("beanSearch") String beanSearch, Model model, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
    if (!beanSearch.isEmpty()) {
      Bean bean = beanService.beanSearch(beanSearch);
      List<Recipe> recipe = recipeService.recipeByBean(bean);

      // ヘッダー用にユーザー情報を渡す
      User user = userDetailsImpl.getUser(); 
      // レシピ作成ユーザーとログインユーザーが同じがチェック
      // key:checkUser ---> true / false
      recipeService.checkRecipeSameUser(model, user, userDetailsImpl);

      model.addAttribute("recipeList", recipe);
      model.addAttribute("searchName", beanSearch);
      model.addAttribute("user", user);

      return "search";
    } else {
      model.addAttribute("errorMessage", "検索ワードがありません");
      System.out.println("検索ワードがない為ホームに戻ります。");
      return "redirect:/";
    }
  }

}

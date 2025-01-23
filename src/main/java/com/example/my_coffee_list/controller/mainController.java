package com.example.my_coffee_list.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.my_coffee_list.entity.Bean;
import com.example.my_coffee_list.entity.Recipe;
import com.example.my_coffee_list.entity.User;
import com.example.my_coffee_list.repository.BeanRepository;
import com.example.my_coffee_list.repository.RecipeRepository;
import com.example.my_coffee_list.security.UserDetailsImpl;
import com.example.my_coffee_list.service.BeanService;
import com.example.my_coffee_list.service.RecipeService;
import com.example.my_coffee_list.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class mainController {
  private UserService userService;
  private RecipeService recipeService;
  private UserDetailsImpl userDetailsImpl;
  private BeanRepository beanRepository;
  private RecipeRepository recipeRepository;
  private BeanService beanService;

  public mainController(UserService userService, RecipeService recipeService, UserDetailsImpl userDetailsImpl,
      BeanRepository beanRepository, BeanService beanService) {
    this.userService = userService;
    this.recipeService = recipeService;
    this.userDetailsImpl = userDetailsImpl;
    this.beanRepository = beanRepository;
    this.beanService = beanService;
  }

  // ホーム画面
  @GetMapping("/")
  public String indexPage(Model model, UserDetailsImpl userDetailsImpl) {
    User user = userDetailsImpl.getUser();
    List<Recipe> RecipeList = recipeService.userRecipeList(user);

    model.addAttribute("user", user);
    model.addAttribute("recipeList", RecipeList);

    return "index";
  }

  // ヘッダーの豆検索フォーム→検索結果へ
  @PostMapping("/search")
  public String beanSearch(@RequestParam("beanSearch") String beanSearch, Model model) {
    if (!beanSearch.isEmpty()) {
      Bean bean = beanService.beanSearch(beanSearch);
      List<Recipe> recipe = recipeService.beanRecipeList(bean);

      model.addAttribute("recipeList", recipe);
      model.addAttribute("searchName", beanSearch);

      return "search";
    }else{
      model.addAttribute("errorMessage", "検索ワードがありません")
      return "redirect:/login";
    }
  }

}

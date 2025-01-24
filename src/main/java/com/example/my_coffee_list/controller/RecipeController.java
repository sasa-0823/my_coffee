package com.example.my_coffee_list.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.my_coffee_list.Form.RecipeForm;
import com.example.my_coffee_list.entity.User;
import com.example.my_coffee_list.security.UserDetailsImpl;
import com.example.my_coffee_list.service.RecipeService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class RecipeController {
  final RecipeService recipeService;

  public RecipeController(RecipeService recipeService) {
    this.recipeService = recipeService;
  }

  // レシピ登録画面に遷移
  @GetMapping("/createRecipe")
  public String createRecipe(Model model, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
    User user = userDetailsImpl.getUser();

    model.addAttribute("user", user);
    model.addAttribute("recipeForm", new RecipeForm());

    return "createRecipe";
  }

  @PostMapping("/createRecipe")
  public String registRecipe(@ModelAttribute @Validated RecipeForm recipeForm, BindingResult bindingResult,HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes,@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {

    if (bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute("recipeForm", recipeForm);
      return "redirect:/createRecipe";
    }

    try {
      recipeService.saveRecipe(recipeForm, userDetailsImpl);
      return "redirect:/";
    } catch (Exception e) {
      System.out.println("エラーが発生しました: " + e.getMessage());
      return "redirect:/createRecipe";
    }
  }
}

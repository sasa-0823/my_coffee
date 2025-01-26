package com.example.my_coffee_list.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    if (model.containsAttribute("recipeForm")) {
      // Flash Attributes から渡された recipeForm をそのまま使用
      RecipeForm recipeForm = (RecipeForm) model.getAttribute("recipeForm");
      model.addAttribute("recipeForm", recipeForm);
  } else {
      // recipeForm が存在しない場合、新しいインスタンスを作成して追加
      model.addAttribute("recipeForm", new RecipeForm());
  }

  return "createRecipe";
  }

  // レシピ登録--->エラーが無ければホームに遷移
  @PostMapping("/createRecipe")
  public String registRecipe(@ModelAttribute @Validated RecipeForm recipeForm, BindingResult bindingResult,
      HttpServletRequest httpServletRequest, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
      RedirectAttributes redirectAttributes, Model model) {

    if (bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipeForm", bindingResult);
      redirectAttributes.addFlashAttribute("recipeForm", recipeForm);
      return "redirect:/createRecipe";
    }

    try {
      recipeService.saveRecipe(recipeForm, userDetailsImpl);
      return "redirect:/";
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("recipeForm", recipeForm);
      System.out.println("エラーが発生しました: " + e.getMessage());
      return "redirect:/createRecipe";
    }
  }

  // レシピ編集画面へ遷移
  @GetMapping("/edit/{recipeId}/{userId}")
  public String editRecipe(@PathVariable("recipeId") Integer recipeId, @PathVariable("userId") Integer userId, RecipeForm recipeForm,
      @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
    // レシピを作った本人以外はホームへ遷移(URL直打ち対策)
    if (userId == userDetailsImpl.getUser().getId()) {
      User user = userDetailsImpl.getUser();
      RecipeForm editRecipe = recipeService.setRecipeToEditFrom(recipeId, recipeForm);

      model.addAttribute("user", user);
      model.addAttribute("recipeForm", editRecipe);
      model.addAttribute("recipeId", recipeId);
      return "editRecipe";
    } else {
      return "redirect:/";
    }
  }

  // レシピ更新--->エラーが無ければホームに遷移
  @PostMapping("/updataRecipe/{recipeId}/{userId}")
  public String updataRecipe(@ModelAttribute @Validated RecipeForm recipeForm, BindingResult bindingResult,
      HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes,
      @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable("recipeId") Integer recipeId, @PathVariable("userId")Integer userId) {

    if (bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipeForm", bindingResult);
      redirectAttributes.addFlashAttribute("recipeForm", recipeForm);
      return "redirect:/edit/{RecipeId}/{UserID}";
    }

    try {
      recipeService.updataRecipe(recipeId, recipeForm, userDetailsImpl);
      return "redirect:/";
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("recipeForm", recipeForm);
      System.out.println("エラーが発生しました: " + e.getMessage());
      return "redirect:/edit/{RecipeId}/{UserID}";
    }
  }

  //レシピを削除
  @GetMapping("/delete/{recipeId}/{userId}")
  public String getMethodName(@PathVariable("recipeId") Integer recipeId, @PathVariable("userId") Integer userId, RecipeForm recipeForm,
  @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
    // レシピを作った本人以外はホームへ遷移(URL直打ち対策)
    if (userId == userDetailsImpl.getUser().getId()){
      recipeService.deleteRecipe(recipeId);
    }
      return "redirect:/";
  }
  
}

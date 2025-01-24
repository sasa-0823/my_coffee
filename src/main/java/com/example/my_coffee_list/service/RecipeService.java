package com.example.my_coffee_list.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.my_coffee_list.Form.RecipeForm;
import com.example.my_coffee_list.entity.Bean;
import com.example.my_coffee_list.entity.Recipe;
import com.example.my_coffee_list.entity.User;
import com.example.my_coffee_list.repository.RecipeRepository;
import com.example.my_coffee_list.security.UserDetailsImpl;

@Service
public class RecipeService {

  private final RecipeRepository recipeRepository;
  private final BeanService beanService;
  
  public RecipeService(RecipeRepository recipeRepository, BeanService beanService) {
    this.recipeRepository = recipeRepository;
    this.beanService = beanService;
  }

  // ユーザーごとのレシピを取得
  public List<Recipe> recipeByUser(User user) {
    return recipeRepository.findByUser(user);
  }

  // 豆ごとのレシピのリストを取得
  public List<Recipe> recipeByBean(Bean bean) {
    return recipeRepository.findByBean(bean);
  }

  // レシピを作成したユーザーとログインしているユーザーが同じかチェック
  // checkRecipeSameUserメソッドで使用
  public boolean checkUser(User user, UserDetailsImpl userDetailsImpl) {
    if (user.getId() == userDetailsImpl.getUser().getId()) {
      return true;
    } else {
      return false;
    }
  }

  // 表示しているレシピがログインユーザーと同じかチェックしてchackUserをキーとして渡す
  public void checkRecipeSameUser(Model model, User user, UserDetailsImpl userDetailsImpl) {
    boolean checkUser = checkUser(user, userDetailsImpl);
    model.addAttribute("checkUser", checkUser);
  }

  public void saveRecipe(RecipeForm recipeForm, UserDetailsImpl userDetailsImpl) {

    Recipe recipe = new Recipe();
    Bean bean = beanService.beanSearch(recipeForm.getName());

    recipe.setUser(userDetailsImpl.getUser());
    recipe.setBean(bean);
    recipe.setRoast(recipeForm.getRoast());
    recipe.setGrindSize(recipeForm.getGrindSize());
    recipe.setBeansWeight(recipeForm.getBeanWeight());
    recipe.setWaterVolume(recipeForm.getWaterValue());
    recipe.setWaterTemp(recipeForm.getWaterTemp());
    recipe.setSteamingTime(recipeForm.getSteamingTime());
    recipe.setDripper(recipeForm.getDoripper());
    recipe.setFilter(recipeForm.getFilter());
    recipe.setMemo(recipeForm.getMemo());

    recipeRepository.save(recipe);

  }

}

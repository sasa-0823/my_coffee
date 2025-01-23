package com.example.my_coffee_list.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.my_coffee_list.entity.Bean;
import com.example.my_coffee_list.entity.Recipe;
import com.example.my_coffee_list.entity.User;
import com.example.my_coffee_list.repository.RecipeRepository;

@Service
public class RecipeService {
  private User user;
  private RecipeRepository recipeRepository;

  public RecipeService(RecipeRepository recipeRepository, User user) {
    this.user = user;
    this.recipeRepository = recipeRepository;
  }

  // ユーザーごとのレシピを取得
  public List<Recipe> userRecipeList(User user){
    return recipeRepository.findByUser(user);
  }

  // 豆ごとのレシピのリストを取得
  public List<Recipe> beanRecipeList(Bean bean){
    return recipeRepository.findByName(bean);
  }

  
}

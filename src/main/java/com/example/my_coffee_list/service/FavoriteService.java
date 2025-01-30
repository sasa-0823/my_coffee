package com.example.my_coffee_list.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.my_coffee_list.entity.Favorite;
import com.example.my_coffee_list.entity.Recipe;
import com.example.my_coffee_list.entity.User;
import com.example.my_coffee_list.repository.FavoriteRepository;
import com.example.my_coffee_list.repository.RecipeRepository;
import com.example.my_coffee_list.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class FavoriteService {
  private final FavoriteRepository favoriteRepository;
  private final UserRepository userRepository;
  private final RecipeRepository recipeRepository;

  public FavoriteService(FavoriteRepository favoriteRepository, UserRepository userRepository,
      RecipeRepository recipeRepository) {
    this.favoriteRepository = favoriteRepository;
    this.userRepository = userRepository;
    this.recipeRepository = recipeRepository;
  }

  // お気に入り登録 <--> お気に入り削除
  @Transactional
  public void favToggle(Integer recipeId, Integer userId) {

    User user = userRepository.findById(userId).orElse(null);
    Recipe recipe = recipeRepository.findById(recipeId).orElse(null);

    if (favoriteRepository.existsByUserAndRecipe(user, recipe)) {
      System.out.println("削除");
      Favorite favorite = favoriteRepository.findByUserAndRecipe(user, recipe);
      favoriteRepository.delete(favorite);
    } else {
      System.out.println("追加");
      Favorite favorite = new Favorite();

      favorite.setUser(user);
      favorite.setRecipe(recipe);

      favoriteRepository.save(favorite);
    }
  }

  // ログインユーザーが特定のレシピのお気に入り状況を確認
  public Boolean checkFavforUser(Integer recipeId, Integer userId){
    User user = userRepository.findById(userId).orElse(null);
    Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
    
    return favoriteRepository.existsByUserAndRecipe(user, recipe);

  }

  // レシピ毎にお気に入りしているかチェック
  public Favorite searchFavRecipe(Recipe recipe) {
    Favorite searchedFav = favoriteRepository.findByRecipe(recipe).orElse(null);
    return searchedFav;
  }

  // ユーザーがレシピをお気に入りしているかチェック
  public boolean checkFavRecipe(Favorite favorite, User user) {
    if (favorite == null) {
      return false;
    } else if ((favorite.getUser().getId()).equals(user.getId())) {
      System.out.println(true);
      return true;
    } else {
      return false;
    }
  }

  // ユーザーがお気に入りしているレシピを一覧で取得
  public List<Favorite> selectedFavPage(User user){
    List<Favorite> favPage = favoriteRepository.findByUser(user);
    return favPage;
  }

  // 特定のユーザーのお気に入り情報を削除(アカウント削除用)
  public void deleteUserFavorite(User user){
    List<Favorite> favorites = favoriteRepository.findByUser(user);
    favoriteRepository.deleteAll(favorites);
  }

}

package com.example.my_coffee_list.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.my_coffee_list.entity.Comment;
import com.example.my_coffee_list.entity.Recipe;
import com.example.my_coffee_list.entity.User;
import com.example.my_coffee_list.repository.CommentRepository;
import com.example.my_coffee_list.repository.RecipeRepository;

@Service
public class CommentService {

  private final CommentRepository commentRepository;
  private final RecipeRepository recipeRepository;

  public CommentService(CommentRepository commentRepository, RecipeRepository recipeRepository) {
    this.commentRepository = commentRepository;
    this.recipeRepository = recipeRepository;
  }

  //特定のレシピのコメントを取得
  public List<Comment> getCommenListforRecipe(Recipe recipe){
    return commentRepository.findByRecipe(recipe);
  }

  // コメントを追加
  public void addComment(User user, Integer recipeid, String NewComment){
    Comment comment = new Comment();
    Recipe recipe = recipeRepository.findById(recipeid).orElse(null);

    comment.setUser(user);
    comment.setRecipe(recipe);
    comment.setText(NewComment);

    commentRepository.save(comment);
  }

  // 特定のユーザーのコメントを削除
  public void deleteUserComment(User user){
    List<Comment> comments = commentRepository.findByUser(user);
    commentRepository.deleteAll(comments);
  }

}

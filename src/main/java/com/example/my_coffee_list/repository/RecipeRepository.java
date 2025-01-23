package com.example.my_coffee_list.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.my_coffee_list.entity.Bean;
import com.example.my_coffee_list.entity.Recipe;
import com.example.my_coffee_list.entity.User;

public interface RecipeRepository extends JpaRepository <Recipe, Integer> {
  public List<Recipe> findById(Bean bean);
  public List<Recipe> findByUser(User user);
  public List<Recipe> findByName(Bean bean);
}

package com.example.my_coffee_list.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.my_coffee_list.entity.Recipe;

public interface RecipeRepository extends JpaRepository <Recipe, Integer> {
  
}

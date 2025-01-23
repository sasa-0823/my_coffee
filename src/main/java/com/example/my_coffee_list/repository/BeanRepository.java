package com.example.my_coffee_list.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.my_coffee_list.entity.Bean;


public interface BeanRepository extends JpaRepository<Bean, Integer> {
  public Bean findByName(String name);
}

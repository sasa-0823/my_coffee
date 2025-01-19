package com.example.my_coffee_list.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.my_coffee_list.entity.Comment;

public interface CommentRepository extends JpaRepository <Comment, Integer> {

}

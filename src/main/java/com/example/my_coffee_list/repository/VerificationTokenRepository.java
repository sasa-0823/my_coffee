package com.example.my_coffee_list.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.my_coffee_list.entity.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {

}

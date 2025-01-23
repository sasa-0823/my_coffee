package com.example.my_coffee_list.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.my_coffee_list.entity.Bean;
import com.example.my_coffee_list.repository.BeanRepository;

@Service
public class BeanService {

  private BeanRepository beanRepository;

  public BeanService(BeanRepository beanRepository) {
    this.beanRepository = beanRepository;
  }


  public Bean beanSearch(String name){
    return beanRepository.findByName(name);}

}

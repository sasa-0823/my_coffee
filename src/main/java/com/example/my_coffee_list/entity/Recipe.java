package com.example.my_coffee_list.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "recipe")
@Data
public class Recipe {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "bean_id")
  private Bean bean;

  @ManyToOne
  @JoinColumn(name = "roasting_id")
  private Roasting roasting;

  @Column(name = "grind_size")
  private String grindSize;

  @Column(name = "beans_value")
  private Integer beansValue;

  @Column(name = "water_volume")
  private Integer waterVolume;

  @Column(name = "water_temp")
  private Integer waterTemp;

  @Column(name = "dripTime")
  private Integer drip_time;

  @Column(name = "dripper")
  private String dripper;

  @Column(name = "memo")
  private String memo;

  @Column(name = "is_view")
  private boolean isView;

  @Column(name = "created_at", insertable = false, updatable = false)
  private Timestamp createdAt;

  @Column(name = "updated_at", insertable = false, updatable = false)
  private Timestamp updatedAt;
}

package com.example.springsilver.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue
    private int categoryId;
    private String categoryName;
}
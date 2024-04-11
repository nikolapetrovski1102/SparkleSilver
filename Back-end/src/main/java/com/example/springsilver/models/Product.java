package com.example.springsilver.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;


    //Dodadeno name na productot
    //TODO Izmeni ja bazata
    private String name;

    private String description;
    private float price;

    //Mozebi treba column name ovde?
    @ManyToOne
    private Category category;

    public Product(String name,String description, float price, Category category) {
        this.name=name;
        this.description = description;
        this.price = price;
        this.category = category;
    }
}

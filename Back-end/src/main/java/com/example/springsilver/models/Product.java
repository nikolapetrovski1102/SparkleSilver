package com.example.springsilver.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;
    private Long quantity;
    //Dodadeno name na productot
    //TODO Izmeni ja bazata
    private String name;

    private String description;
    private float price;

    //Mozebi treba column name ovde?
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @JoinColumn(name = "image_pathurl")
    private String imagePathURL;

    public Product(Long quantity, String name, String description, float price, Category category, String imagePathURL) {
        this.quantity = quantity;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imagePathURL = imagePathURL;
    }
}

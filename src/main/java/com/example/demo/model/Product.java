package com.example.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "product_description")
    private String description;

    @Column(name = "product_price")
    private double price;

    @Column(name = "product_brand")
    private String brand;

    @Column(name = "product_image_url")
    private String fileUrl;

    @Column(name = "product_stock")
    private int stock;

    @Column(name = "product_created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "product_status")
    private String status;

    @Column(name = "product_discount")
    private double discount;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Transient
    private MultipartFile imageFile;

    public Product(){
        super();
        this.createdAt = new Date();
        this.status = "available";
        this.discount = 0.0;
    }

    public Product(String name, String description, double price, String brand, String fileUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.brand = brand;
        this.fileUrl = fileUrl;
        this.createdAt = new Date();
        this.status = "available";
        this.discount = 0.0;
    }

    public Product(String name, String description, double price, String brand, String fileUrl, Category category, MultipartFile imageFile) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.brand = brand;
        this.fileUrl = fileUrl;
        this.category = category;
        this.imageFile = imageFile;
        this.createdAt = new Date();
        this.status = "available";
        this.discount = 0.0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}

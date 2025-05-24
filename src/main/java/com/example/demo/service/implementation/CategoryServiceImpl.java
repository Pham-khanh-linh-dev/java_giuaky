package com.example.demo.service.implementation;

import com.example.demo.model.Category;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category category) {
        category.setStatus("active");
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {
        var existedCategory = categoryRepository.findById(category.getCategoryId()).get();
        existedCategory.setCategoryName(category.getCategoryName());
        existedCategory.setDescription(category.getDescription());
        existedCategory.setImageUrl(category.getImageUrl());
        existedCategory.setStatus(category.getStatus());
        return categoryRepository.save(existedCategory);
    }

    @Override
    public void delete(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).get();
    }

    @Override
    public void initDb() {
        if (categoryRepository.findAll().stream().noneMatch(c -> "Smartphones".equalsIgnoreCase(c.getCategoryName()))) {
            Category category1 = new Category();
            category1.setCategoryName("Smartphones");
            category1.setDescription("Latest smartphones from top brands");
            category1.setStatus("active");
            categoryRepository.save(category1);
        }
        if (categoryRepository.findAll().stream().noneMatch(c -> "Laptops".equalsIgnoreCase(c.getCategoryName()))) {
            Category category2 = new Category();
            category2.setCategoryName("Laptops");
            category2.setDescription("High-performance laptops for work and gaming");
            category2.setStatus("active");
            categoryRepository.save(category2);
        }
        if (categoryRepository.findAll().stream().noneMatch(c -> "Tablets".equalsIgnoreCase(c.getCategoryName()))) {
            Category category3 = new Category();
            category3.setCategoryName("Tablets");
            category3.setDescription("Versatile tablets for productivity and entertainment");
            category3.setStatus("active");
            categoryRepository.save(category3);
        }
        if (categoryRepository.findAll().stream().noneMatch(c -> "Accessories".equalsIgnoreCase(c.getCategoryName()))) {
            Category category4 = new Category();
            category4.setCategoryName("Accessories");
            category4.setDescription("Essential tech accessories and peripherals");
            category4.setStatus("active");
            categoryRepository.save(category4);
        }
    }
}

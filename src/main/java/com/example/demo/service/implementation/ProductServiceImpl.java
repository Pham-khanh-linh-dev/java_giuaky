package com.example.demo.service.implementation;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Value("${upload.directory}")
    private String uploadDirectory;
    @Override
    public void save(Product product, MultipartFile file) throws IOException{
        saveImage(product, file);
        product.setCreatedAt(new Date());
        product.setStatus("available");
        product.setStock(0);
        product.setDiscount(0.0);
        productRepository.save(product);
    }

    @Override
    public Product update(Product product, MultipartFile file) throws IOException {
        var existedProduct = productRepository.findById(product.getId()).get();
        existedProduct.setName(product.getName());
        existedProduct.setBrand(product.getBrand());
        existedProduct.setDescription(product.getDescription());
        existedProduct.setPrice(product.getPrice());
        existedProduct.setCategory(product.getCategory());
        existedProduct.setStock(product.getStock());
        existedProduct.setStatus(product.getStatus());
        existedProduct.setDiscount(product.getDiscount());
        if(file != null) {
            saveImage(existedProduct, file);
        }
        return productRepository.save(existedProduct);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Category> getAllCategories() {
        return productRepository.findAllCategories();
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public List<Product> getSortedProducts(boolean ascending) {
        if(ascending)
            return productRepository.findAllByOrderByPriceAsc();
        return productRepository.findAllByOrderByPriceDesc();
    }

    @Override
    public List<Product> getProductsByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> findRelatedProduct(Product product) {
        return productRepository.findRelatedProductsByCategoryAndExcludeProduct(
                product.getCategory().getCategoryId(),
                product.getId()
        );
    }

    @Override
    public void initDB() {
        if (productRepository.findAll().stream().noneMatch(p -> "iPhone 15 Pro".equalsIgnoreCase(p.getName()))) {
            Product product1 = new Product();
            product1.setName("iPhone 15 Pro");
            product1.setPrice(999.0);
            product1.setBrand("Apple");
            product1.setDescription("Latest iPhone with A17 Pro chip and titanium design");
            product1.setCategory(new Category(1L));
            product1.setFileUrl("../uploads/iphone15pro.jpg");
            product1.setStock(100);
            product1.setStatus("available");
            product1.setDiscount(0.0);
            product1.setCreatedAt(new Date());
            productRepository.save(product1);
        }
        if (productRepository.findAll().stream().noneMatch(p -> "Samsung Galaxy S24 Ultra".equalsIgnoreCase(p.getName()))) {
            Product product2 = new Product();
            product2.setName("Samsung Galaxy S24 Ultra");
            product2.setPrice(1199.0);
            product2.setBrand("Samsung");
            product2.setDescription("Flagship Android phone with S Pen and AI features");
            product2.setCategory(new Category(1L));
            product2.setFileUrl("../uploads/s24ultra.jpg");
            product2.setStock(80);
            product2.setStatus("available");
            product2.setDiscount(0.0);
            product2.setCreatedAt(new Date());
            productRepository.save(product2);
        }
        if (productRepository.findAll().stream().noneMatch(p -> "MacBook Pro M3".equalsIgnoreCase(p.getName()))) {
            Product product3 = new Product();
            product3.setName("MacBook Pro M3");
            product3.setPrice(1999.0);
            product3.setBrand("Apple");
            product3.setDescription("Powerful laptop with M3 chip and Liquid Retina display");
            product3.setCategory(new Category(2L));
            product3.setFileUrl("../uploads/macbookpro.jpg");
            product3.setStock(50);
            product3.setStatus("available");
            product3.setDiscount(0.0);
            product3.setCreatedAt(new Date());
            productRepository.save(product3);
        }
        if (productRepository.findAll().stream().noneMatch(p -> "iPad Pro 12.9".equalsIgnoreCase(p.getName()))) {
            Product product4 = new Product();
            product4.setName("iPad Pro 12.9");
            product4.setPrice(1099.0);
            product4.setBrand("Apple");
            product4.setDescription("Professional tablet with M2 chip and ProMotion display");
            product4.setCategory(new Category(3L));
            product4.setFileUrl("../uploads/ipadpro.jpg");
            product4.setStock(60);
            product4.setStatus("available");
            product4.setDiscount(0.0);
            product4.setCreatedAt(new Date());
            productRepository.save(product4);
        }
        if (productRepository.findAll().stream().noneMatch(p -> "AirPods Pro 2".equalsIgnoreCase(p.getName()))) {
            Product product5 = new Product();
            product5.setName("AirPods Pro 2");
            product5.setPrice(249.0);
            product5.setBrand("Apple");
            product5.setDescription("Wireless earbuds with active noise cancellation");
            product5.setCategory(new Category(4L));
            product5.setFileUrl("../uploads/airpodspro.jpg");
            product5.setStock(200);
            product5.setStatus("available");
            product5.setDiscount(0.0);
            product5.setCreatedAt(new Date());
            productRepository.save(product5);
        }
        if (productRepository.findAll().stream().noneMatch(p -> "Samsung Galaxy Tab S9 Ultra".equalsIgnoreCase(p.getName()))) {
            Product product6 = new Product();
            product6.setName("Samsung Galaxy Tab S9 Ultra");
            product6.setPrice(1199.0);
            product6.setBrand("Samsung");
            product6.setDescription("Premium Android tablet with S Pen and AMOLED display");
            product6.setCategory(new Category(3L));
            product6.setFileUrl("../uploads/tabs9ultra.jpg");
            product6.setStock(40);
            product6.setStatus("available");
            product6.setDiscount(0.0);
            product6.setCreatedAt(new Date());
            productRepository.save(product6);
        }
        if (productRepository.findAll().stream().noneMatch(p -> "Dell XPS 15".equalsIgnoreCase(p.getName()))) {
            Product product7 = new Product();
            product7.setName("Dell XPS 15");
            product7.setPrice(1799.0);
            product7.setBrand("Dell");
            product7.setDescription("Premium laptop with InfinityEdge display and RTX graphics");
            product7.setCategory(new Category(2L));
            product7.setFileUrl("../uploads/xps15.jpg");
            product7.setStock(30);
            product7.setStatus("available");
            product7.setDiscount(0.0);
            product7.setCreatedAt(new Date());
            productRepository.save(product7);
        }
        if (productRepository.findAll().stream().noneMatch(p -> "Samsung Galaxy Watch 6 Pro".equalsIgnoreCase(p.getName()))) {
            Product product8 = new Product();
            product8.setName("Samsung Galaxy Watch 6 Pro");
            product8.setPrice(449.0);
            product8.setBrand("Samsung");
            product8.setDescription("Premium smartwatch with rotating bezel and health features");
            product8.setCategory(new Category(4L));
            product8.setFileUrl("../uploads/watch6pro.jpg");
            product8.setStock(100);
            product8.setStatus("available");
            product8.setDiscount(0.0);
            product8.setCreatedAt(new Date());
            productRepository.save(product8);
        }
    }

    private void saveImage(Product product, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            Path uploadPath = Paths.get(uploadDirectory);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            // Save the image file to the local directory
            String filename = file.getOriginalFilename();
            Path filePath = Paths.get(uploadDirectory, filename);
            Files.write(filePath, file.getBytes());

            product.setFileUrl("../uploads/" + filename);
        }
    }
}

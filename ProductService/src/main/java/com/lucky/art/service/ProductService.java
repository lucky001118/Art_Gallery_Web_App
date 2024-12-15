package com.lucky.art.service;

import com.lucky.art.exception.ProductException;
import com.lucky.art.externalModel.Seller;
import com.lucky.art.model.Product;
import com.lucky.art.request.createProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    public Product createProduct(createProductRequest req, Seller seller);
    public void deleteProduct(Long productId) throws ProductException;
    public Product updateProduct(Long productId,Product product) throws ProductException;
    public Product findProductById(Long productId) throws ProductException;
    List<Product> searchProduct(String query);
    public Page<Product> getAllProducts(
            String category,
            String brand,
            String colors,
            String sizes,
            Integer minPrice,
            Integer maxPrice,
            Integer minDiscount,
            String sort,
            String stock,
            Integer pageNumber
    );

    public List<Product> getProductBySellerId(Long sellerId);
}

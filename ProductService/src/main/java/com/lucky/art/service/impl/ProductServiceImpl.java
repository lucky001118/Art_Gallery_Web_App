package com.lucky.art.service.impl;

import com.lucky.art.exception.ProductException;
import com.lucky.art.externalModel.Seller;
import com.lucky.art.model.Category;
import com.lucky.art.model.Product;
import com.lucky.art.repository.CategoryRepository;
import com.lucky.art.repository.ProductRepository;
import com.lucky.art.request.createProductRequest;
import com.lucky.art.service.ProductService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public Product createProduct(createProductRequest req, Seller seller) {
        Category category1 = categoryRepository.findByCategoryId(req.getCategory());

        if (category1==null){
            Category category = new Category();
            category.setCategoryId(req.getCategory());
            category.setLevel(1);
            category1 = categoryRepository.save(category);
        }
        Category category2 = categoryRepository.findByCategoryId(req.getCategory2());

        if (category2==null){
            Category category = new Category();
            category.setCategoryId(req.getCategory2());
            category.setLevel(2);
            category.setParentCategory(category1);
            category2 = categoryRepository.save(category);
        }
        Category category3 = categoryRepository.findByCategoryId(req.getCategory3());

        if (category3==null){
            Category category = new Category();
            category.setCategoryId(req.getCategory3());
            category.setLevel(3);
            category.setParentCategory(category2);
            category3 = categoryRepository.save(category);
        }
//        Calculating the discount percentage
        int discountPercentage = calculateDiscountPercentage(req.getMrpPrice(),req.getSellingPrice());

        Product product = new Product();
        product.setSeller(seller);
        product.setSellerId(seller.getId());
        product.setCategory(category3);
        product.setDescription(req.getDescription());
        product.setCreatedAt(LocalDateTime.now());
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setSellingPrice(req.getSellingPrice());
        product.setImages(req.getImages());
        product.setMrpPrice(req.getMrpPrice());
        product.setSizes(req.getSize());
        product.setDiscountPercentage(discountPercentage);

        return productRepository.save(product);
    }

    private int calculateDiscountPercentage(int mrpPrice, int sellingPrice) {
        if (mrpPrice<=0){
            throw new IllegalArgumentException("Actual price must be grater then zero");
        }
        double discount = mrpPrice-sellingPrice;
        double discountPercentage = (discount/mrpPrice)*100;
        return (int)discountPercentage;
    }

    @Override
    public void deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);
        productRepository.delete(product);
    }

    @Override
    public Product updateProduct(Long productId, Product product) throws ProductException {
        findProductById(productId);
        product.setId(productId);
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long productId) throws ProductException {
        return productRepository.findById(productId)
                .orElseThrow(()->new ProductException("Product not found with this id "+productId));
    }

    @Override
    public List<Product> searchProduct(String query) {
        return productRepository.searchProduct(query);
    }

    @Override
    public Page<Product> getAllProducts(String category, String brand, String colors, String sizes, Integer minPrice, Integer maxPrice, Integer minDiscount,String sort, String stock, Integer pageNumber) {
        Specification<Product> spec=(root,query,criteriaBuilder)->{
            List<Predicate> predicates = new ArrayList<>();

            if (category!=null){
                Join<Product,Category> categoryJoin = root.join("category");
                predicates.add(criteriaBuilder.equal(categoryJoin.get("categoryId"),category));
            }

            if (colors!=null && !colors.isEmpty()){
                System.out.println("Colors "+colors);
                predicates.add(criteriaBuilder.equal(root.get(("color")),colors));
            }

            //filter by size (single value)
            if (sizes!=null && !sizes.isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("size"),sizes));
            }

            if (maxPrice!=null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("sellingPrice"),minPrice));
            }

            if (maxPrice!=null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("sellingPrice"),maxPrice));
            }

            if (minDiscount!=null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("discountPercentage"),minDiscount));
            }

            if (stock!=null){
                predicates.add(criteriaBuilder.equal(root.get("stock"),stock));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Pageable pageable;
        if (sort!=null && !sort.isEmpty()){
            pageable = switch (sort) {
                case "price_low" -> PageRequest.of(pageNumber != null ? pageNumber : 0, 10,
                        Sort.by("sellingPrice").ascending());
                case "price_high" -> PageRequest.of(pageNumber != null ? pageNumber : 0, 10,
                        Sort.by("sellingPrice").descending());
                default -> PageRequest.of(pageNumber != null ? pageNumber : 0, 10,
                        Sort.by("sellingPrice").descending());
            };
        }else {
            pageable=PageRequest.of(pageNumber!=null?pageNumber:0,10,Sort.unsorted());
        }
        return productRepository.findAll(spec,pageable);
    }

    @Override
    public List<Product> getProductBySellerId(Long sellerId) {

        return productRepository.findBySellerId(sellerId);
    }
}
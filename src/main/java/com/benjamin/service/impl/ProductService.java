package com.benjamin.service.impl;

import com.benjamin.model.CategoryModel;
import com.benjamin.model.ProductModel;
import com.benjamin.model.SellerModel;
import com.benjamin.repository.ICategoryRepository;
import com.benjamin.repository.IProductRepository;
import com.benjamin.request.CreateProductRequest;
import com.benjamin.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;

    @Override
    public ProductModel createProduct(CreateProductRequest request, SellerModel seller) {
        CategoryModel category1 = categoryRepository.findByCategoryId(request.getCategory());
        if (category1 == null) {
            CategoryModel category = CategoryModel.builder()
                    .categoryId(request.getCategory())
                    .level(1)
                    .build();
            category1 = categoryRepository.save(category);
        }

        CategoryModel category2 = categoryRepository.findByCategoryId(request.getCategory2());
        if (category2 == null) {
            CategoryModel category = CategoryModel.builder()
                    .categoryId(request.getCategory2())
                    .level(2)
                    .parentCategory(category1)
                    .build();
            category2 = categoryRepository.save(category);
        }

        CategoryModel category3 = categoryRepository.findByCategoryId(request.getCategory3());
        if (category3 == null) {
            CategoryModel category = CategoryModel.builder()
                    .categoryId(request.getCategory3())
                    .level(3)
                    .parentCategory(category2)
                    .build();
            category3 = categoryRepository.save(category);
        }

        int discountPercentage = calculateDiscountPercentage(double mrpPrice, double sellingPrice);

        ProductModel product = ProductModel.builder()
                .seller(seller)
                .category(category3)
                .description(request.getDescription())
                .createdAt(LocalDateTime.now())
                .title(request.getTitle())
                .color(request.getColor())
                .sellingPrice(request.getSellingPrice())
                .images(request.getImages())
                .mrpPrice(request.getMrpPrice())
                .size(request.getSize())
                .discountPercentage()
                .build();

        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {

    }

    @Override
    public ProductModel getById(Long id) {
        return null;
    }

    @Override
    public ProductModel updateProduct(Long id, ProductModel productModel) {
        return null;
    }

    @Override
    public List<ProductModel> searchProduct() {
        return List.of();
    }

    @Override
    public Page<ProductModel> getAll(String category, String brand, String colors, String sizes, Integer minimumPrice, Integer maximumPrice, Integer minDiscount, String sort, String stock, Integer pageNumber) {
        return null;
    }

    @Override
    public List<ProductModel> getProductBySeller(Long sellerId) {
        return List.of();
    }
}

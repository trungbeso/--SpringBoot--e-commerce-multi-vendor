package com.benjamin.service;

import com.benjamin.model.ProductModel;
import com.benjamin.model.SellerModel;
import com.benjamin.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {
    ProductModel createProduct(CreateProductRequest request, SellerModel seller);
    void deleteProduct(Long id);
    ProductModel getById(Long id);
    ProductModel updateProduct(Long id, ProductModel productModel);
    List<ProductModel> searchProduct();
    Page<ProductModel> getAll(String category, String brand, String colors, String sizes, Integer minimumPrice, Integer maximumPrice, Integer minDiscount, String sort, String stock, Integer pageNumber);
    List<ProductModel> getProductBySeller(Long sellerId);
}

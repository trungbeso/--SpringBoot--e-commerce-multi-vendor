package com.benjamin.service;

import com.benjamin.domain.EAccountStatus;
import com.benjamin.model.SellerModel;

import java.util.List;

public interface ISellerService {
    SellerModel getSellerProfile(String jwt);
    SellerModel createSeller(SellerModel sellerModel);
    SellerModel getById(Long id);
    SellerModel getByEmail(String email);
    List<SellerModel> getAll(EAccountStatus status);
    SellerModel update(Long id, SellerModel sellerModel);
    void delete(Long id);
    SellerModel verifyEmail(String email, String otp);
    SellerModel updateSellerAccountStatus(Long id, EAccountStatus status);
}

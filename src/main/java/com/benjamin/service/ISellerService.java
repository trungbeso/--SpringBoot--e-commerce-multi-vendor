package com.benjamin.service;

import com.benjamin.domain.EAccountStatus;
import com.benjamin.exception.SellerException;
import com.benjamin.model.SellerModel;

import java.util.List;

public interface ISellerService {
    SellerModel getSellerProfile(String jwt);
    SellerModel createSeller(SellerModel sellerModel);
    SellerModel getById(Long id) throws SellerException;
    SellerModel getByEmail(String email);
    List<SellerModel> getAll(EAccountStatus status);
    SellerModel update(Long id, SellerModel sellerModel) throws SellerException;
    void delete(Long id) throws SellerException;
    SellerModel verifyEmail(String email, String otp);
    SellerModel updateSellerAccountStatus(Long id, EAccountStatus status) throws SellerException;
}

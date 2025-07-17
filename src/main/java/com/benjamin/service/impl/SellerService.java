package com.benjamin.service.impl;

import com.benjamin.config.JwtProvider;
import com.benjamin.domain.EAccountStatus;
import com.benjamin.model.AddressModel;
import com.benjamin.model.SellerModel;
import com.benjamin.repository.IAddressRepository;
import com.benjamin.repository.ISellerRepository;
import com.benjamin.repository.IUserRepository;
import com.benjamin.service.ISellerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.benjamin.domain.EUserRole.ROLE_SELLER;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SellerService implements ISellerService {

    ISellerRepository sellerRepository;
    JwtProvider jwtProvider;
    PasswordEncoder passwordEncoder;
    IAddressRepository addressRepository;

    @Override
    public SellerModel getSellerProfile(String jwt) {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        return this.getByEmail(email);
    }

    @Override
    public SellerModel createSeller(SellerModel sellerModel) {
        SellerModel seller = sellerRepository.findByEmail(sellerModel.getEmail());
        if (seller != null) {
            throw new RuntimeException("Seller already exists with email: " + sellerModel.getEmail());
        }
        AddressModel savedAddress = addressRepository.save(sellerModel.getPickupAddress());
        SellerModel newSeller = SellerModel.builder()
                .email(sellerModel.getEmail())
                .password(passwordEncoder.encode(sellerModel.getPassword()))
                .sellerName(sellerModel.getSellerName())
                .phoneNumber(sellerModel.getPhoneNumber())
                .pickupAddress(savedAddress)
                .GTIN(sellerModel.getGTIN())
                .role(ROLE_SELLER)
                .bankDetails(sellerModel.getBankDetails())
                .businessDetails(sellerModel.getBusinessDetails())
                .build();
        return sellerRepository.save(newSeller);
    }

    @Override
    public SellerModel getById(Long id) {
        return sellerRepository.findById(id).orElseThrow(() -> new RuntimeException("Seller not found with id: " + id));
    }

    @Override
    public SellerModel getByEmail(String email) {
        SellerModel seller = sellerRepository.findByEmail(email);
        if (seller == null) {
            throw new RuntimeException("Seller not found with email: " + email);
        }
        return seller;
    }

    @Override
    public List<SellerModel> getAll(EAccountStatus status) {
        return sellerRepository.findByAccountStatus(status);
    }

    @Override
    public SellerModel update(Long id, SellerModel seller) {
        SellerModel existingSeller = this.getById(id);
        if (seller.getSellerName() != null ){
            existingSeller.setSellerName(seller.getSellerName());
        }
        if (seller.getPhoneNumber() != null ){
            existingSeller.setPhoneNumber(seller.getPhoneNumber());
        }
        if (seller.getEmail() != null ){
            existingSeller.setEmail(seller.getEmail());
        }
        if (seller.getBankDetails() != null
                && seller.getBankDetails().getAccountHolderName() != null
                && seller.getBankDetails().getIfscCode() != null
                && seller.getBankDetails().getAccountNumber() != null){
            existingSeller.getBankDetails().setAccountHolderName(seller.getBankDetails().getAccountHolderName());
            existingSeller.getBankDetails().setIfscCode(seller.getBankDetails().getIfscCode());
            existingSeller.getBankDetails().setAccountNumber(seller.getBankDetails().getAccountNumber());
        }
        if (seller.getBusinessDetails() != null
                && seller.getBusinessDetails().getBusinessName() != null){
            existingSeller.getBusinessDetails().setBusinessName(seller.getBusinessDetails().getBusinessName());
        }
        if (seller.getGTIN() != null ){
            existingSeller.setGTIN(seller.getGTIN());
        }
        if (seller.getPickupAddress() != null
                && seller.getPickupAddress().getAddress() != null
                && seller.getPickupAddress().getPhoneNumber() != null
                && seller.getPickupAddress().getCity() != null
                && seller.getPickupAddress().getState() != null){
            existingSeller.getPickupAddress().setAddress(seller.getPickupAddress().getAddress());
            existingSeller.getPickupAddress().setPhoneNumber(seller.getPickupAddress().getPhoneNumber());
            existingSeller.getPickupAddress().setCity(seller.getPickupAddress().getCity());
            existingSeller.getPickupAddress().setState(seller.getPickupAddress().getState());
        }
        return sellerRepository.save(existingSeller);
    }

    @Override
    public void delete(Long id) {
        SellerModel seller = this.getById(id);
        sellerRepository.delete(seller);
    }

    @Override
    public SellerModel verifyEmail(String email, String otp) {
        SellerModel seller = this.getByEmail(email);
        seller.setEmailVerified(true);
        return sellerRepository.save(seller);
    }

    @Override
    public SellerModel updateSellerAccountStatus(Long id, EAccountStatus status) {
        SellerModel seller = this.getById(id);
        seller.setAccountStatus(status);
        return sellerRepository.save(seller);
    }
}

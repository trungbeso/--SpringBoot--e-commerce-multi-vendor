package com.benjamin.controller;

import com.benjamin.config.JwtProvider;
import com.benjamin.domain.EAccountStatus;
import com.benjamin.model.SellerModel;
import com.benjamin.model.SellerReport;
import com.benjamin.model.VerificationCode;
import com.benjamin.repository.IVerificationCodeRepository;
import com.benjamin.request.LoginOtpRequest;
import com.benjamin.request.LoginRequest;
import com.benjamin.response.ApiResponse;
import com.benjamin.response.AuthResponse;
import com.benjamin.service.*;
import com.benjamin.util.OtpUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.benjamin.util.Constant.SELLER_PREFIX;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/v1/sellers")
public class SellerController {

    ISellerService sellerService;
    IVerificationCodeRepository verificationCodeRepository;
    IVerificationService verificationService;
    IAuthService authService;
    IEmailService emailService;
    JwtProvider jwtProvider;
    ISellerReportService sellerReportService;

//    @PostMapping("/sent/login-otp")
//    public ResponseEntity<ApiResponse> sendOtpHandler(@RequestBody LoginOtpRequest request) {
//        authService.sentLoginOtp(request.getEmail(), request.getRole());
//
//        ApiResponse response = new ApiResponse();
//        response.setMessage("OTP sent successfully");
//
//        return ResponseEntity.ok(response);
//    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginSeller(@RequestBody LoginRequest request) {
        String email = request.getEmail();

        request.setEmail(SELLER_PREFIX + email);
        AuthResponse response = authService.signing(request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/verify/{otp}")
    public ResponseEntity<SellerModel> verifySellerEmail(@PathVariable String otp) {
        VerificationCode verificationCode = verificationCodeRepository.findByOtp(otp);
        if (verificationCode == null || !verificationCode.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }
        SellerModel seller = sellerService.verifyEmail(verificationCode.getEmail(), otp);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SellerModel> createSeller(@RequestBody SellerModel seller) {
        SellerModel savedSeller = sellerService.createSeller(seller);

        String otp = OtpUtil.generateOtp();
        VerificationCode verificationCode = VerificationCode.builder()
                .otp(otp)
                .email(seller.getEmail())
                .build();
        verificationCodeRepository.save(verificationCode);

        String subject = "Ecommerce Site Email Verification Code";
        String text = "Welcome to My Site. Your verify your account using this link -";
        String url = "http://localhost:3000/api/v1/sellers/verify-seller/";
        emailService.sendVerificationEmail(seller.getEmail(), verificationCode.getOtp(), subject, text + url);
        return new ResponseEntity<>(savedSeller, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerModel> getSellerById(@PathVariable Long id) {
        SellerModel seller = sellerService.getById(id);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<SellerModel> getSellerByJwt(@RequestHeader("Authorization") String jwt) {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        SellerModel seller = sellerService.getByEmail(email);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

//    @GetMapping("/report")
//    public ResponseEntity<SellerReport> getSellerReport(@RequestHeader("Authorization") String jwt){
//        String email = jwtProvider.getEmailFromJwtToken(jwt);
//        SellerModel seller = sellerService.getByEmail(email);
//        SellerReport report =
//    }

    @GetMapping
    public ResponseEntity<List<SellerModel>> getAllSellers(
            @RequestParam(required = false)EAccountStatus status){
        List<SellerModel> sellers = sellerService.getAll(status);
        return ResponseEntity.ok(sellers);
    }

    @PatchMapping()
    public ResponseEntity<SellerModel> updateSeller(@RequestHeader("Authorization") String jwt, @RequestBody SellerModel seller){
        SellerModel profile = sellerService.getSellerProfile(jwt);
        SellerModel updatedSeller = sellerService.update(profile.getId(), seller);
        return ResponseEntity.ok(updatedSeller);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) {
        sellerService.delete(id);
        return ResponseEntity.noContent().build();
    }


}

package com.benjamin.repository;

import com.benjamin.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    VerificationCode findByEmail(String email);

    VerificationCode findByOtp(String otp);
}

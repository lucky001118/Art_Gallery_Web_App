package com.lucky.art.repository;

import com.lucky.art.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode,Long> {
    VerificationCode findByEmail(String email);

    void deleteById(Long id);
    VerificationCode findByOtp(String otp);
}

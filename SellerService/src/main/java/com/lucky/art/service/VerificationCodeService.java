package com.lucky.art.service;

import com.lucky.art.model.VerificationCode;

public interface VerificationCodeService {
    VerificationCode findCodeByEmail(String email);
    void deleteVerificationCode(Long id);
    void saveVerificationCode(VerificationCode code);
}

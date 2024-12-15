package com.lucky.art.service.impl;

import com.lucky.art.model.VerificationCode;
import com.lucky.art.repository.VerificationCodeRepository;
import com.lucky.art.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {
    @Autowired
    private VerificationCodeRepository verificationCodeRepository;
    @Override
    public VerificationCode findCodeByEmail(String email) {
        return verificationCodeRepository.findByEmail(email);
    }

    @Override
    public void deleteVerificationCode(Long id) {
        verificationCodeRepository.deleteById(id);
    }

    @Override
    public void saveVerificationCode(VerificationCode code) {
        verificationCodeRepository.save(code);
    }
}

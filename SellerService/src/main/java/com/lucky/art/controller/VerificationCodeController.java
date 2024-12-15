package com.lucky.art.controller;

import com.lucky.art.model.VerificationCode;
import com.lucky.art.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verification")
public class VerificationCodeController {

    @Autowired
    private VerificationCodeService verificationCodeService;

    @GetMapping("/code/{email}")
    public ResponseEntity<VerificationCode> findCodeByEmail(@PathVariable String email){
        VerificationCode code = verificationCodeService.findCodeByEmail(email);
        return new ResponseEntity<>(code, HttpStatus.OK);
    }
    @DeleteMapping("/deleteCode/{codeEmail}")
    public ResponseEntity<Void> deleteVerificationCode(@PathVariable("codeEmail") String codeEmail){
        VerificationCode codeDetails = verificationCodeService.findCodeByEmail(codeEmail);
        Long id = codeDetails.getId();
        verificationCodeService.deleteVerificationCode(id);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<Void> saveVerificationCode(@RequestBody  VerificationCode code){
        System.out.println(code.getEmail()+" "+code.getOtp());
        verificationCodeService.saveVerificationCode(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

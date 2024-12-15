package com.lucky.art.controller;

import com.lucky.art.domain.AccountStatus;
import com.lucky.art.externalService.UserService;
import com.lucky.art.model.Seller;
import com.lucky.art.model.SellerReport;
import com.lucky.art.model.VerificationCode;
import com.lucky.art.repository.VerificationCodeRepository;
import com.lucky.art.request.LoginRequest;
import com.lucky.art.response.AuthResponse;
import com.lucky.art.service.EmailService;
import com.lucky.art.service.SellerReportService;
import com.lucky.art.service.SellerService;
import com.lucky.art.utils.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController {
    private final SellerService sellerService;
    private final VerificationCodeRepository verificationCodeRepository;
    private final UserService userService;
    private final EmailService emailService;
    private final SellerReportService sellerReportService;

    //    ------------------------- internal service controlling method -----------------------
    @PostMapping("/createUser")
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) throws Exception {
        Seller saverSeller = sellerService.createSeller(seller);

        String otp = OtpUtil.generateOtp();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(seller.getEmail());
        verificationCodeRepository.save(verificationCode);

        String subject = "Art Gallery Email Verification Code";
        String text = "Welcome the Art Gallery, verify your account using this link: ";
        String frontend_url = "http://localhost:8085/verify-seller";

        emailService.sendVerificationEmail(seller.getEmail(), verificationCode.getOtp(), subject,text + frontend_url);
        return new  ResponseEntity<>(saverSeller, HttpStatus.CREATED);
    }

    @GetMapping("/findSeller/{email}")
    public ResponseEntity<Seller> findSellerByEmail(@PathVariable String email) throws Exception {
        Seller seller = sellerService.findByEmailId(email);
     return new ResponseEntity<>(seller,HttpStatus.OK);
    }

//    ------------------------- internal service controlling method -----------------------
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginSeller(
            @RequestBody LoginRequest req) throws Exception {
        String  otp = req.getOtp();
        String email = req.getEmail();

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(email);
        if (verificationCode==null || !verificationCode.getOtp().equals(otp)){
            throw new Exception("wrong otp");
        }

        req.setEmail("seller_"+email);
        AuthResponse authResponse = userService.loginSellr(req);

        return ResponseEntity.ok(authResponse);
    }

    @PatchMapping("/verify/{otp}")
    public ResponseEntity<Seller> verifySellerEmail(@PathVariable String otp) throws Exception{

        VerificationCode verificationCode = verificationCodeRepository.findByOtp(otp);

        if (verificationCode == null || !verificationCode.getOtp().equals(otp)){
            throw new Exception("wrong otp .....");
        }

        Seller seller = sellerService.verifyEmail(verificationCode.getEmail(),otp);
        return new ResponseEntity<>(seller,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Long id) throws Exception{
        Seller seller = sellerService.getSellerById(id);
        return new ResponseEntity<>(seller,HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<Seller> getSellerByJwt(
            @RequestHeader("Authorization") String jwt) throws Exception{
//        String email = userService.getEmailFromJwtToken(jwt);
        Seller seller = sellerService.getSellProfile(jwt);   //we are finding the seller profile by the jwt token
        return new ResponseEntity<>(seller,HttpStatus.OK);
    }

    @GetMapping("/report")
    public ResponseEntity<SellerReport> getSellerReport(
            @RequestHeader("Authorization") String jwt) throws Exception{
//        String email = userService.getEmailFromJwtToken(jwt);
        Seller seller = sellerService.getSellProfile(jwt);
        SellerReport report = sellerReportService.getSellerReport(seller);
        return new ResponseEntity<>(report,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Seller>> getAllSeller(@RequestParam(required = false)AccountStatus status) throws Exception {
        List<Seller> sellers = sellerService.getAllSellers(status);
        return ResponseEntity.ok(sellers);
    }

    @PatchMapping()
    public ResponseEntity<Seller> updateSeller(@RequestHeader("Authorization") String jwt,
                                               @RequestBody Seller seller) throws Exception{

        Seller profile = sellerService.getSellProfile(jwt);
        Seller updatedSeller = sellerService.updateSeller(profile.getId(), seller);
        return ResponseEntity.ok(updatedSeller);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) throws Exception{

        sellerService.deleteSeller(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sellerReport")
    public ResponseEntity<SellerReport> getSellerReport(@RequestParam Seller seller){
        SellerReport sellerReport = sellerReportService.getSellerReport(seller);
        return new ResponseEntity<>(sellerReport,HttpStatus.OK);
    }

    @PutMapping("/update/sellerReport")
    public ResponseEntity<SellerReport> updateSellerReport(@RequestBody SellerReport sellerReport){
        SellerReport sellerReport1 = sellerReportService.updateSellerReport(sellerReport);
        return new ResponseEntity<>(sellerReport1,HttpStatus.ACCEPTED);
    }

}

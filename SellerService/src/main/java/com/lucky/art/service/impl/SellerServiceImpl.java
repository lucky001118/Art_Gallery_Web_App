package com.lucky.art.service.impl;

import com.lucky.art.domain.AccountStatus;
import com.lucky.art.externalModel.Address;
import com.lucky.art.externalModel.USER_ROLE;
import com.lucky.art.externalService.UserService;
import com.lucky.art.model.Seller;
import com.lucky.art.repository.SellerRepository;
import com.lucky.art.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;
    private final UserService userService;
    @Override
    public Seller createSeller(Seller seller) throws Exception {
        System.out.println(seller.getEmail());
        Seller sellerExist = sellerRepository.findByEmail(seller.getEmail());
        if (sellerExist!=null){
            throw new Exception("seller already exist, with provide email id");
        }
        Address savedAddress = userService.saveAddressOfSeller(seller.getPickupAddress());

        Seller newSeller = new Seller();
        newSeller.setPassword(userService.EncodeSellerPass(seller.getPassword()));
        newSeller.setSellerName(seller.getSellerName());
        newSeller.setEmail(seller.getEmail());
        newSeller.setPickupAddress(savedAddress);
        newSeller.setGSTIN(seller.getGSTIN());
        newSeller.setRole(USER_ROLE.ROLE_SELLER);
        newSeller.setMobile(seller.getMobile());
        newSeller.setBankDetails(seller.getBankDetails());
        newSeller.setBusinessDetails(seller.getBusinessDetails());
        newSeller.setPickupAddressId(savedAddress.getId());

        return sellerRepository.save(newSeller);
    }

    @Override
    public Seller findByEmailId(String email) throws Exception {
        Seller seller = sellerRepository.findByEmail(email);

        if (seller == null){
            throw new Exception("Seller not found");
        }

        seller.setPickupAddress(userService.findAddressbyId(seller.getPickupAddressId()));
        return seller;
    }

    @Override
    public Seller getSellProfile(String jwt) throws Exception {
        String email = userService.getEmailFromJwtToken(jwt);
        Seller seller = this.findByEmailId(email);
        Address address = userService.findAddressbyId(seller.getPickupAddressId());
        seller.setPickupAddress(address);
        return seller;
    }

    @Override
    public Seller getSellerById(Long id) throws Exception {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(()->new Exception("seller not fount with provided id "+id));
        Address add = userService.findAddressbyId(seller.getPickupAddressId());
        System.out.println(seller.getPickupAddress());
        seller.setPickupAddress(add);
        return seller;
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus status) throws Exception {
        List<Seller> seller =  sellerRepository.findByAccountStatus(status);
        List<Seller> aSellers = new ArrayList<>();
        for (Seller se: seller){
            Address add = userService.findAddressbyId(se.getPickupAddressId());
            se.setPickupAddress(add);
            aSellers.add(se);
        }
        return aSellers;
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) throws Exception {
        Seller existingSeller = this.getSellerById(id);

        if (seller.getSellerName()!=null){
            existingSeller.setSellerName(seller.getSellerName());
        }
        if (seller.getMobile()!=null){
            existingSeller.setMobile(seller.getMobile());
        }
        if (seller.getEmail()!=null){
            existingSeller.setEmail(seller.getEmail());
        }
        if (seller.getBusinessDetails()!=null
                && seller.getBusinessDetails().getBusinessName()!=null){
            existingSeller.getBusinessDetails().
                    setBusinessName(seller.getBusinessDetails().getBusinessName());
        }
        if (seller.getBankDetails()!=null
                && seller.getBankDetails().getAccountHolderName() !=null
        && seller.getBankDetails().getIfscCode()!=null
        && seller.getBankDetails().getAccountNumber()!=null){

            existingSeller.getBankDetails().setAccountHolderName(
                    seller.getBankDetails().getAccountHolderName());
            existingSeller.getBankDetails().setIfscCode(
                    seller.getBankDetails().getIfscCode());
            existingSeller.getBankDetails().setAccountNumber(
                    seller.getBankDetails().getAccountNumber());
        }
        if (seller.getPickupAddress()!=null
                && seller.getPickupAddress().getAddress()!=null
        && seller.getPickupAddress().getMobile()!=null
        && seller.getPickupAddress().getCity()!=null
        && seller.getPickupAddress().getState()!=null){

            existingSeller.getPickupAddress().setAddress(
                    seller.getPickupAddress().getAddress());
            existingSeller.getPickupAddress().setCity(
                    seller.getPickupAddress().getCity());
            existingSeller.getPickupAddress().setMobile(
                    seller.getPickupAddress().getMobile());
            existingSeller.getPickupAddress().setState(
                    seller.getPickupAddress().getState());
            existingSeller.getPickupAddress().setPinCode(
                    seller.getPickupAddress().getPinCode());
        }
        if (seller.getGSTIN()!=null){
            existingSeller.setGSTIN(seller.getGSTIN());
        }
        if (seller.getPickupAddress()!=null){
            Address add = userService.updateAddressbyId(existingSeller.getPickupAddressId(),seller.getPickupAddress());
            existingSeller.setPickupAddress(add);
        }

        return sellerRepository.save(existingSeller);
    }

    @Override
    public void deleteSeller(Long id) throws Exception {
        Seller seller = getSellerById(id);
        userService.deleteAddressbyId(seller.getPickupAddressId());
        sellerRepository.delete(seller);
    }

    @Override
    public Seller verifyEmail(String email, String otp) throws Exception {
        Seller seller = findByEmailId(email);
        seller.setEmailVerified(true);
        return sellerRepository.save(seller);
    }

    @Override
    public Seller updateSellerAccountStatus(Long id, AccountStatus status) throws Exception {
        Seller seller = getSellerById(id);
        seller.setAccountStatus(status);
        return sellerRepository.save(seller);
    }
}

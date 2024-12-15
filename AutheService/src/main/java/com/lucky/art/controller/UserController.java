package com.lucky.art.controller;

import com.lucky.art.config.JwtProvider;
import com.lucky.art.model.Address;
import com.lucky.art.model.User;
import com.lucky.art.repository.AddressRepository;
import com.lucky.art.repository.UserRepository;
import com.lucky.art.request.LoginRequest;
import com.lucky.art.response.AuthResponse;
import com.lucky.art.service.AuthService;
import com.lucky.art.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;

    @GetMapping("users/profile")
    public ResponseEntity<User> createUserHandler(
            @RequestHeader("Authorization") String jwt)
            throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        return ResponseEntity.ok(user);
    }

    //    ---------- external service usage controller -------------
    @GetMapping("/getEmail")
    public ResponseEntity<String> getEmailFromJwtToken(
            @RequestHeader("Authorization")String jwt) throws Exception {
        String email = jwtProvider.getEmailFromToken(jwt);
        return ResponseEntity.ok(email);
    }

    @PostMapping("/loginSeller")
    public ResponseEntity<AuthResponse> loginSellr(@RequestBody LoginRequest req){
        AuthResponse res = authService.signing(req);

        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @PostMapping("auth/pass")
    public ResponseEntity<String> EncodeSellerPass(@RequestBody String pass){
        String encodePassword = passwordEncoder.encode(pass);
        return new ResponseEntity<>(encodePassword, HttpStatus.ACCEPTED);
    }

    @GetMapping("/address/{id}")
    public ResponseEntity<Address> findAddressbyId(@PathVariable Long id) throws Exception{
        Address address = addressRepository.findById(id).orElseThrow(()-> new Exception("addres not found by this id "+id));
        return new ResponseEntity<>(address,HttpStatus.OK);
    }

    @PutMapping("/address/{id}")
    public ResponseEntity<Address> updateAddressbyId(@PathVariable Long id,@RequestBody Address add) throws Exception{
        Address address = addressRepository.findById(id).orElseThrow(()-> new Exception("addres not found by this id "+id));
        address.setName(add.getName());
        address.setMobile(add.getMobile());
        address.setCity(add.getCity());
        address.setState(add.getState());
        address.setLocality(add.getLocality());
        address.setAddress(add.getAddress());
        address.setPinCode(add.getPinCode());

        Address updatedAdd = addressRepository.save(address);
        return new ResponseEntity<>(updatedAdd,HttpStatus.OK);
    }

    @DeleteMapping("/address/{id}")
    public ResponseEntity<Void> deleteAddressbyId(@PathVariable Long id) throws Exception{
        addressRepository.deleteById(id);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws Exception{
        User user = userRepository.findById(id).orElseThrow(()-> new Exception("User not found with this id "+id));
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/getUser")
    public ResponseEntity<User> getUserFromJwtToken (
            @RequestHeader("Authorization")String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        return ResponseEntity.ok(user);
    }
}

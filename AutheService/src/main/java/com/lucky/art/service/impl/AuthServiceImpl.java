package com.lucky.art.service.impl;

import com.lucky.art.config.JwtProvider;
import com.lucky.art.domain.USER_ROLE;
import com.lucky.art.externalModel.Cart;
import com.lucky.art.externalModel.Seller;
import com.lucky.art.externalModel.VerificationCode;
import com.lucky.art.externalService.CartService;
import com.lucky.art.externalService.SellerService;
import com.lucky.art.model.User;
import com.lucky.art.repository.UserRepository;
import com.lucky.art.request.LoginRequest;
import com.lucky.art.request.SignupRequest;
import com.lucky.art.response.AuthResponse;
import com.lucky.art.service.AuthService;
import com.lucky.art.service.EmailService;
import com.lucky.art.utils.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartService cartService;
    private final JwtProvider jwtProvider;
    private final SellerService sellerService;
    private final EmailService emailService;
    private final CustomeUserServiceImpl customeUserService;
    @Override
    public String createUser(SignupRequest req) throws Exception {

        VerificationCode verificationCode = sellerService.findCodeByEmail(req.getEmail());
        if (verificationCode==null || !verificationCode.getOtp().equals(req.getOtp())){
            throw new Exception("Wrong otp ....");
        }

        User user = userRepository.findByEmail(req.getEmail());

        if (user==null){
            User createUser = new User();
            createUser.setEmail(req.getEmail());
            createUser.setFullName(req.getFullName());
            createUser.setRole(USER_ROLE.ROLE_CUSTOMER);
            createUser.setMobile("7247657122");
            createUser.setPassword(passwordEncoder.encode(req.getOtp()));

            user = userRepository.save(createUser);

            Cart cart = new Cart();
            cart.setUser(user);
            cart.setUserId(user.getId());
            cartService.createCart(cart);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(req.getEmail(),null,authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return JwtProvider.generateToken(authentication);
    }

    @Override
    public void sentLoginOtp(String email,USER_ROLE role) throws Exception {
        String SIGNING_PREFIX="signing_";

        if (email.startsWith(SIGNING_PREFIX)){
            email=email.substring(SIGNING_PREFIX.length());
            System.out.println(role);
            if (role.equals(USER_ROLE.ROLE_SELLER)){
//                System.out.println(role);
                Seller seller = sellerService.findSellerByEmail(email);
                if (seller==null){
                    throw new Exception("seller not found");
                }
            }else {
                User user = userRepository.findByEmail(email);
                if (user==null){
                    throw new Exception("User not found with provided email");
                }
            }

        }
        VerificationCode isExist = sellerService.findCodeByEmail(email);
//        System.out.println(isExist.getEmail()+" "+isExist.getOtp());
        if (isExist!=null){
//            sellerService.deleteVerificationCode(isExist);
            sellerService.deleteVerificationCode(isExist.getEmail());
        }

        if (isExist==null){
            String otp = OtpUtil.generateOtp();
            VerificationCode verificationCode = new VerificationCode();
            verificationCode.setOtp(otp);
            verificationCode.setEmail(email);
            sellerService.saveVerificationCode(verificationCode);
            String subject = "Art Gallery login/signup otp";
            String text = "your login/signup otp is this "+otp;

            emailService.sendVerificationEmail(email,otp,subject,text);

            System.out.println(verificationCode.getOtp()+" "+verificationCode.getEmail());
        }

    }

    //signing method for the loging
    @Override
    public AuthResponse signing(LoginRequest req) {
        String username = req.getEmail();
        String otp = req.getOtp();

        Authentication authentication = authenticate(username,otp);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwtToken(token);
        authResponse.setMessage("Login Success");

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roleName = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        authResponse.setRole(USER_ROLE.valueOf(roleName));
        return authResponse;
    }

    private Authentication authenticate(String username, String otp) {
        UserDetails userDetails = customeUserService.loadUserByUsername(username);

        String SELLER_PREFIX="seller_";
        if (username.startsWith(SELLER_PREFIX)){
            username = username.substring(SELLER_PREFIX.length());
        }

        if (userDetails==null){
            throw new BadCredentialsException("invalid username");
        }

        VerificationCode verificationCode = sellerService.findCodeByEmail(username);
        if (verificationCode==null || !verificationCode.getOtp().equals(otp) ){
            throw new BadCredentialsException("wrong otp");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,
                null,userDetails.getAuthorities());
    }

}

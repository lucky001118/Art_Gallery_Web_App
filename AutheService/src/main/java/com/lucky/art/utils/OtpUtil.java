package com.lucky.art.utils;

import java.util.Random;

public class OtpUtil {
    public static String generateOtp(){
        int OTP_LENGTH = 6;
        Random random = new Random();
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for (int i=0;i<OTP_LENGTH;i++){
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
}

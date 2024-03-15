package com.otpexample.otpexample.Service;


import com.otpexample.otpexample.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailVerificationService {

    @Autowired
    EmailService emailService;
    @Autowired
    UserService userService;
    static final Map<String,String> emailOtpMapping = new HashMap<>();

    public Map<String, String> verifyOtp(String email, String otp){
        String storeOtp = emailOtpMapping.get(email);

        Map<String,String> response = new HashMap<>();
        if(storeOtp!=null && storeOtp.equals(otp)){
            User user = userService.getUserByEmail(email);
            if (user!=null){
                emailOtpMapping.remove(email);
                userService.verifyEmail(user);
                response.put("status","success");
                response.put("message","Email Verified Succesfully");
            }else{
                response.put("status","error");
                response.put("message","User not found");
            }
        }else{
            response.put("status","error");
            response.put("message","Invalid OTP");

        }
        return response;
    }
    public Map<String, String> sendOtpForLogin(String email){
        //check user exit and is he verified
        if(userService.isEmailVerified(email)){
            String otp = emailService.generateOtp();
            emailOtpMapping.put(email, otp);

            //send OTP to the user's email
            emailService.sendOTPEmail(email);

            Map<String, String> response = new HashMap<>();
            response.put("status","success");
            response.put("message","OTP sent Successfully");
            return response;
        }else {
            Map<String, String> response = new HashMap<>();
            response.put("status","error");
            response.put("message","Email is not verified");
            return response;
        }
    }

    public Map<String, String> verifyOtpForLogin(String email, String otp) {
        String storedOtp = emailOtpMapping.get(email);
        Map<String, String> response = new HashMap<>();
        if(storedOtp!=null && storedOtp.equals(otp)){
            emailOtpMapping.remove(email);
            //OTP Valid
            response.put("status","success");
            response.put("message","OTP verified Successfully");
        }else{
            //Invalid OTP
            response.put("status","error");
            response.put("message","Invalid Otp");
        }
        return response;
    }
}



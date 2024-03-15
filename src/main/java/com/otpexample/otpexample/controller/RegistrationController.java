package com.otpexample.otpexample.controller;




import com.otpexample.otpexample.Service.EmailService;
import com.otpexample.otpexample.Service.EmailVerificationService;
import com.otpexample.otpexample.Service.UserService;
import com.otpexample.otpexample.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RegistrationController {
    @Autowired
    private EmailVerificationService emailVerificationService;
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    public Map<String, String> registerUser(@RequestBody User user){
        User registeredUser = userService.userRegister(user);

        //send otp for email verification
        emailService.sendOTPEmail(user.getEmail());

        Map<String, String> response = new HashMap<>();
        response.put("status","success");
        response.put("message","User registered succesfully. check your email for for verification");
        return response;
    }
    @PostMapping("/verify-otp")
    public Map<String, String> verifyOtp(@RequestParam String email, @RequestParam String otp){
        return emailVerificationService.verifyOtp(email,otp);
    }
}

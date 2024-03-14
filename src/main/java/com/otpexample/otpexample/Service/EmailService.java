package com.otpexample.otpexample.Service;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import static com.otpexample.otpexample.Service.EmailVerificationService.emailOtpMapping;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserService userService;

    public EmailService(JavaMailSender javaMailSender, UserService userService) {
        this.javaMailSender = javaMailSender;
        this.userService = userService;
    }

    //This method will generate 6 digit OTP number. random number it will genertate
    //String has a format method which format output
    public String generateOtp(){
        return String.format("%06d", new java.util.Random().nextInt(1000000));
    }

    public void sendOTPEmail(String email) {
        String otp = generateOtp();
        //save the OTP for later verification
        emailOtpMapping.put(email, otp);
        sendEmail(email,"OTP for Verification", "Your OTP is: "+otp);
    }
    private void sendEmail(String to, String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setText(text);
        message.setSubject(subject);
        javaMailSender.send(message);
    }
}

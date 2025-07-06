package com.benjamin.service;

public interface IEmailService {
    void sendVerificationEmail(String userEmail, String otp, String subject, String text);
}

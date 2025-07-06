package com.benjamin.util;

import java.util.Random;

public class OtpUtil {

    public static String generateOtp() {
        int length = 6;
        Random rd = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(rd.nextInt(9));
        }
        return sb.toString();
    }
}

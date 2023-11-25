package com.fit.usernluappsbapi;


import org.springframework.security.crypto.bcrypt.BCrypt;

public class main {
    public static void main(String[] args) {
        String password = "123";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        hashedPassword = "$2a$10$qnPgP4STbhSjnR6c6Ga4yeCOQv6jZbXy5D38eSDcwupWZwngeNpD2";
        System.out.println("Mật khẩu đã mã hóa: " + hashedPassword);

        // Kiểm tra mật khẩu
        String candidatePassword = "123";
        if (BCrypt.checkpw(candidatePassword, hashedPassword)) {
            System.out.println("Mật khẩu đúng!");
        } else {
            System.out.println("Mật khẩu sai!");
        }
    }
}

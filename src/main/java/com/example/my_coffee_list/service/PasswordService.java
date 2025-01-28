package com.example.my_coffee_list.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.example.my_coffee_list.entity.User;
import com.example.my_coffee_list.repository.UserRepository;

@Service
public class PasswordService {

    private final UserRepository userRepository;

    public PasswordService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 確認用のパスワードが１回目入力のパスワードと一致しているか
    public boolean isSamePassword(String password, String passwordConfimation) {
        return password.equals(passwordConfimation);
    }

    // ランダムパスワード生成
    public String randomPw(int count) {
        while (true) {
            String result = "";
            String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

            java.util.Random ran = new java.util.Random();

            for (int i = 0; i < count; i++) {
                int pos = ran.nextInt(base.length());
                result += base.charAt(pos);
            }

            if (!result.matches(".*[a-z].*") || !result.matches(".*[A-Z].*")) {
                continue;
            }

            return result;
        }
    }

    // 新しいメールアドレスを送信
    public void sendNewPw(String password, String email, String name) {

        String senderAddress = "sasahara.yukio.08@gmail.com";
        String recipientAddress = email;
        String subject = "メール認証";
        String message = "仮パスワードは以下の通りです";
        String sendPw = password;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(senderAddress);
        mailMessage.setTo(recipientAddress);
        mailMessage.setSubject(subject);
        mailMessage.setText(name + "様/n" + message + "/n" + sendPw);

    }

    // 新しいパスワードをDBに保存
    public void registPw(String password, String email) {
        User user = userRepository.findByEmail(email);
        user.setPassword(password);
        userRepository.save(user);
    }

    // 新しいパスワードをDBに保存
    public void registPw(String password, User user) {
        user.setPassword(password);
        userRepository.save(user);
    }
}

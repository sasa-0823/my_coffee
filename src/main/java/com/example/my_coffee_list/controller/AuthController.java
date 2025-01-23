package com.example.my_coffee_list.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;

import com.example.my_coffee_list.Form.SignupForm;
import com.example.my_coffee_list.entity.User;
import com.example.my_coffee_list.entity.VerificationToken;
import com.example.my_coffee_list.event.SignupEventPublisher;
import com.example.my_coffee_list.service.UserService;
import com.example.my_coffee_list.service.VerificationTokenService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    private final UserService userService;
    private final SignupEventPublisher signupEventPublisher;
    private final VerificationTokenService verificationTokenService;

    public AuthController(UserService userService, SignupEventPublisher signupEventPublisher,
            VerificationTokenService verificationTokenService) {
        this.userService = userService;
        this.signupEventPublisher = signupEventPublisher;
        this.verificationTokenService = verificationTokenService;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    // 新規登録画面遷移時
    @GetMapping("/signup")
    public String to_signUp_page(Model model) {
        if (!model.containsAttribute("signupForm")) {
            model.addAttribute("signupForm", new SignupForm());
        }
        return "auth/signUp";
    }

    // 新規登録時(新規登録画面->ログイン画面へ遷移)
    @PostMapping("/signup")
    public String signUp_regist(@ModelAttribute @Validated SignupForm signupForm,
            BindingResult bindingResult,
            HttpServletRequest httpServletRequest,
            Model model) {
        if (userService.isEmailRegistered(signupForm.getEmail())) {
            FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "既に登録済みのメールアドレスです");
            bindingResult.addError(fieldError);
        }

        if (!userService.isSamePassword(signupForm.getPassword(), signupForm.getPasswordConfirmation())) {
            FieldError fieldError = new FieldError(bindingResult.getObjectName(), "passwordConfirmation",
                    "パスワードが一致しません");
            bindingResult.addError(fieldError);
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("signupForm", signupForm);
            return "auth/signUp";
        }

        User createdUser = userService.createUser(signupForm);
        String requestUrl = new String(httpServletRequest.getRequestURL());
        signupEventPublisher.publishSignupEvent(createdUser, requestUrl);
        model.addAttribute("successMessage",
                "ご入力いただいたメールアドレスに認証メールを送信しました。メールに記載されているリンクをクリックし、会員登録を完了してください。");
        return "auth/verify";
    }

    @GetMapping("/signup/verify")
    public String verify(@RequestParam(name = "token") String token, RedirectAttributes redirectAttributes) {
        VerificationToken verificationToken = verificationTokenService.getVerificationToken(token); // トークンがDBにあるか検索
        if (verificationToken != null) {
            User user = verificationToken.getUser();
            userService.enableUser(user); // UserのEnabledをtrueに変更
            String successMessage = "会員登録が完了しました。";
            redirectAttributes.addFlashAttribute("authSuccessMessage", successMessage);
        } else {
            String errorMessage = "トークンが無効です。";
            redirectAttributes.addFlashAttribute("authErrorMessage", errorMessage);
        }
        return "redirect:/login";
    }

}

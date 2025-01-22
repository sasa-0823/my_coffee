package com.example.my_coffee_list.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.my_coffee_list.Form.SignupForm;
import com.example.my_coffee_list.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/signUp")  //新規登録画面遷移時
    public String to_signUp_page(Model model) {
        model.addAttribute("signUp", new SignupForm());
        return "auth/signUp";
    }

    @PostMapping("/signUp") //新規登録時(新規登録画面->ログイン画面へ遷移)
    public String signUp_regist(Model model, Locale locale,
    @Validated SignupForm signupForm, BindingResult bindingResult,
    RedirectAttributes redirectAttributes) {
        if (userService.isEmailRegistered(signupForm.getEmail())){
            FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "既に登録済みのメールアドレスです");
            bindingResult.addError(fieldError);
        }

        if (userService.isSamePassword(signupForm.getPassword(), signupForm.getPasswordConfirmation())) {
            FieldError fieldError = new FieldError(bindingResult.getObjectName(), "passwordConfirmation", "パスワードが一致しません");
            bindingResult.addError(fieldError);
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("signupForm", signupForm);
            return "auth/signUp";
        }

        userService.createUser(signupForm);
        redirectAttributes.addFlashAttribute("successMessage", "会員登録が完了しました。");
        return "redirect:/login";
    }

}

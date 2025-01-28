package com.example.my_coffee_list.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.my_coffee_list.Form.SignupForm;
import com.example.my_coffee_list.Form.UpdataPwFrom;
import com.example.my_coffee_list.entity.User;
import com.example.my_coffee_list.security.UserDetailsImpl;
import com.example.my_coffee_list.service.PasswordService;
import com.example.my_coffee_list.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PasswordController {
private final UserService userService;
private final PasswordService passwordService;

    public PasswordController(UserService userService, PasswordService passwordService) {
    this.userService = userService;
    this.passwordService = passwordService;
}
    // パスワード再設定(仮パスワード送信)
    @GetMapping("/resetPw/{email}")
    public String resetPw(@RequestParam("email") String email, RedirectAttributes redirectAttributes){
        if(userService.selectUserForEmail(email) != null){
            String name = userService.selectUserForEmail(email).getName();
            String newPw = passwordService.randomPw(8);
            passwordService.sendNewPw(newPw, email, name);
            passwordService.registPw(newPw, email);
            redirectAttributes.addFlashAttribute("Message", "仮パスワードをメールにて送信しました。");
            return "redirect:/";
        }else{
            redirectAttributes.addFlashAttribute("Message", "メールアドレスが存在しません。");
            return "redirect:/";
        }
    }

    // パスワード変更(マイページからパスワード再設定画面へ遷移)
    @GetMapping("/updataPw/")
    public String updataPw(Model model){
        if(!model.containsAttribute("updataPwForm")){
            model.addAttribute("updataPwForm", new UpdataPwFrom());
        }
        return "updataPw";
    }

    // パスワード更新
    @PostMapping("/updataPw")
    public String updataPw(@ModelAttribute @Validated UpdataPwFrom updataPwFrom,
    UserDetailsImpl userDetailsImpl,
    BindingResult bindingResult,
    HttpServletRequest httpServletRequest,
    Model model){
        if(!passwordService.isSamePassword(updataPwFrom.getAfterPw(), updataPwFrom.getAfterPwConfirmation())){
            FieldError fieldError = new FieldError(bindingResult.getObjectName(), "afterPwConfirmation", "パスワードが一致しません");
            bindingResult.addError(fieldError);
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("updataPwForm", updataPwFrom);
            return "updataPw";
        }

        User user = userDetailsImpl.getUser();
        String password = updataPwFrom.getAfterPw();
        passwordService.registPw(password, user);
        
        return "index";
    }
    


}

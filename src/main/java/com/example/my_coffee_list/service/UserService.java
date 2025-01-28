package com.example.my_coffee_list.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.my_coffee_list.Form.SignupForm;
import com.example.my_coffee_list.entity.User;
import com.example.my_coffee_list.repository.UserRepository;

@Service
public class UserService {

  @Value("${image.folder}")
  private String imgFolder;

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional // トランザクション化
  public User createUser(SignupForm signupForm) {
    User user = new User();

    user.setName(signupForm.getName());
    user.setEmail(signupForm.getEmail());
    user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
    user.setEnabled(false);
    user.setImg(signupForm.getEmail() + ".jpg");
    System.out.println(user.getImg());
    
    //アイコンをディレクトリに保存
    saveFile(signupForm);
    System.out.println("保存後処理");
  
    return userRepository.save(user);
  }

  // ユーザーアイコンの保存
  public void saveFile(SignupForm signupForm) {
    if (!signupForm.getImg().isEmpty()) {
      System.out.println("画像登録あり");
      try {
        // 保存する画像ファイルのパスを指定
        var saveFile = signupForm.getEmail() + ".jpg";  //画像ファイル名
        Path imgFilePath = Path.of(imgFolder, saveFile);  //保存先パス

        // ファイルがない場合は生成
        if (!Files.exists(imgFilePath.getParent())) {
          Files.createDirectories(imgFilePath.getParent());
        }

        // 画像の保存
        Files.copy(signupForm.getImg().getInputStream(), imgFilePath, StandardCopyOption.REPLACE_EXISTING);

      } catch (IOException e) {
        throw new RuntimeException("ファイル保存中にエラーが発生: " + e.getMessage(), e);
      }
    }else{
      System.out.println("画像登録なし");
    }
  }


  // 登録するメールアドレスが既に存在しているか確認
  public boolean isEmailRegistered(String email) {
    User user = userRepository.findByEmail(email);
    return user != null; // nullの場合false
  }

  //メール認証後enableadをtrue
  @Transactional
    public void enableUser(User user) {
        user.setEnabled(true);
        userRepository.save(user);
    } 

  // ユーザー書き換え
  public void changeUser(User user){
    userRepository.save(user);
  }

  // ユーザーの削除
  public void deleteUser(User user){
    userRepository.delete(user);
  }

  // ユーザーの検索(email)
  public User selectUserForEmail(String email){
    return userRepository.findByEmail(email);
  }

  
}

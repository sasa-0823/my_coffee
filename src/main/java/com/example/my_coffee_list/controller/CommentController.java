package com.example.my_coffee_list.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import com.example.my_coffee_list.entity.User;
import com.example.my_coffee_list.security.UserDetailsImpl;
import com.example.my_coffee_list.service.CommentService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class CommentController {
  final CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }
  
  // コメント追加
  @PostMapping("/Comment/{recipeId}")
  public String addComment(RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PathVariable("recipeId") Integer recipeId, @RequestParam("newComment") String comment) {    User user = userDetailsImpl.getUser();
    commentService.addComment(user, recipeId, comment);
    redirectAttributes.addFlashAttribute("message", "コメントしました");
      return "redirect:/";
  }

  
  // コメント編集
  @GetMapping("editComment/{CommentId}")
  public String editComment(@PathVariable("commentId") Integer commentId, @RequestParam("newComment") String comment, HttpServletRequest request){
    
    commentService.updataComment(commentId, comment);
    
    //元の画面に遷移
    String resUrl = request.getHeader("Referer");
    System.out.println(resUrl);
    return "redirect:" + resUrl;
  }

  // コメント削除
  @GetMapping("/deleteComment/{commentId}")
  public String deleteComment(@PathVariable("commentId") Integer commentId, HttpServletRequest request){
    commentService.deleteComment(commentId);

    //元の画面に遷移
    String resUrl = request.getHeader("Referer");
    System.out.println(resUrl);
    return "redirect:" + resUrl;
  }

}

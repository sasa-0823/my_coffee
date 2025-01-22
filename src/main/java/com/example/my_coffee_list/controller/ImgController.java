package com.example.my_coffee_list.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.my_coffee_list.service.StorageService;

import java.io.IOException;

@Controller
public class ImgController {
    private final StorageService storageService;

    @Autowired
    public ImgController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/signUp")
    public String UploadImage(@RequestParam("file") MultipartFile file){
        storageService.store()
    }


}

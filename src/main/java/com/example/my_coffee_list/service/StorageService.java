package com.example.my_coffee_list.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	// private final Path rootLocation;

	// @Autowired


	//ストレージの初期化、ストレージシステムを設定準備に使用。
    // void init(){
	// 	try {
	// 		Files.createDirectories(rootLocation);
	// 	}catch{
	// 		throw new RuntimeException("Could not initialize storage", e);
	// 	}
	// }

	void store(MultipartFile file);  //MultipartFile型のファイルをストレージに保存

	Stream<Path> loadAll();  //指定されたファイル名のファイルをStream形式で取得する

	Path load(String filename);  //指定されたファイル名のファイルパスを取得する

	Resource loadAsResource(String filename);  //指定されたファイル名のファイルをResource形式で取得する

	void deleteAll();  //ストレージ内のすべてのファイルを削除
}

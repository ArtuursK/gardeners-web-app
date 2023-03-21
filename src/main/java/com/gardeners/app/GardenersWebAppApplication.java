package com.gardeners.app;

import com.gardeners.app.services.FileStorageService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GardenersWebAppApplication implements CommandLineRunner {

	@Resource
	FileStorageService fileStorageService;

	public static void main(String[] args) {
		SpringApplication.run(GardenersWebAppApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
		//fileStorageService.deleteAll();
		fileStorageService.init();
	}

}

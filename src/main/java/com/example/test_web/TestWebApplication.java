package com.example.test_web;

import com.example.test_web.db.DBConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.nio.charset.Charset;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class TestWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestWebApplication.class, args);
		System.out.println("Default Charset: " + Charset.defaultCharset());
	}

}

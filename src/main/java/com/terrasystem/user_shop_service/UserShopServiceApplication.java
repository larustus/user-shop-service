package com.terrasystem.user_shop_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class UserShopServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserShopServiceApplication.class, args);
	}

}

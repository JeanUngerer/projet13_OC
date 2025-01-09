package com.yourcaryourway.chatappback.poc_back;

import com.yourcaryourway.chatappback.poc_back.properties.RsaKeyProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Slf4j
@EnableConfigurationProperties({RsaKeyProperties.class})
@SpringBootApplication
public class PocBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocBackApplication.class, args);
	}

}

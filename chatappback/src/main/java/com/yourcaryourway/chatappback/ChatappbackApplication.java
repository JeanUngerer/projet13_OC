package com.yourcaryourway.chatappback;

import com.yourcaryourway.chatappback.entities.User;
import com.yourcaryourway.chatappback.properties.RsaKeyProperties;
import com.yourcaryourway.chatappback.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.ArrayList;
import java.util.Date;

@EnableConfigurationProperties({RsaKeyProperties.class})
@SpringBootApplication
public class ChatappbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatappbackApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserService userService, PasswordEncoder passwordEncoder){
        return args -> {
            User supporUser = new User();
            supporUser.setId(1L);
            supporUser.setFirstName("John");
            supporUser.setLastName("Doe");
            supporUser.setEmail("support@yourcaryourway.com");
            supporUser.setPassword(passwordEncoder.encode("password"));
            supporUser.setAddress("123 Main St");
            supporUser.setBirthDate(new Date());
            supporUser.setReservations(new ArrayList<>());
            supporUser.setChats(new ArrayList<>());
            supporUser.setIsCustomer(false);

            User customerUser = new User();
            customerUser.setId(1L);
            customerUser.setFirstName("Jolly");
            customerUser.setLastName("dee");
            customerUser.setEmail("customer1@exemple.com");
            customerUser.setPassword(passwordEncoder.encode("password"));
            customerUser.setAddress("123 Main St");
            customerUser.setBirthDate(new Date());
            customerUser.setReservations(new ArrayList<>());
            customerUser.setChats(new ArrayList<>());
            customerUser.setIsCustomer(true);


            User customer2User = new User();
            customer2User.setId(1L);
            customer2User.setFirstName("Jane");
            customer2User.setLastName("Doe");
            customer2User.setEmail("customer2@exemple.com");
            customer2User.setPassword(passwordEncoder.encode("password"));
            customer2User.setAddress("123 Main St");
            customer2User.setBirthDate(new Date());
            customer2User.setReservations(new ArrayList<>());
            customer2User.setChats(new ArrayList<>());
            customer2User.setIsCustomer(true);

            userService.create(supporUser);
            userService.create(customerUser);
            userService.create(customer2User);
        };
    }
}
package com.aston.rest;

import com.aston.rest.config.SpringConfig;
import com.aston.rest.dto.UserDto;
import com.aston.rest.entity.User;
import com.aston.rest.service.RestService;
import com.aston.rest.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationRunner {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        applicationContext.refresh();
        UserService userService = applicationContext.getBean(UserService.class);
        UserDto userDto = userService.findById(1L);
        System.out.println(userDto);
    }
}

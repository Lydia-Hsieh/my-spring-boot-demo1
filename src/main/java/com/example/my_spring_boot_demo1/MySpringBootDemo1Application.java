package com.example.my_spring_boot_demo1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(basePackages = "com.example.my_spring_boot_demo1.dao.mybatis")
@EnableScheduling
public class MySpringBootDemo1Application {

	public static void main(String[] args) {
		SpringApplication.run(MySpringBootDemo1Application.class, args);
	}

}

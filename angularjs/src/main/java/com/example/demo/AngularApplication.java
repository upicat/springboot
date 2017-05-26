package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;

@SpringBootApplication
@RestController
public class AngularApplication {

	public static void main(String[] args) {
		SpringApplication.run(AngularApplication.class, args);
	}
	
	@RequestMapping("/search")
	public Person search() {
		return new Person("xm", 12, "SH");
	}
	
	@Data
	@AllArgsConstructor
	class Person {
		String name;
		Integer age;
		String address;
	}
}

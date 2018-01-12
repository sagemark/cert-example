package com.sausage.certexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CertExampleApplication {

	@GetMapping("/need-cert")
	String needCert(){
		return "you did it!";
	}

	public static void main(String[] args) {
		SpringApplication.run(CertExampleApplication.class, args);
	}
}

package com.trymad.litechess_monolith;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class LitechessMonolithApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LitechessMonolithApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

	}

}

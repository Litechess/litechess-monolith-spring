package com.trymad.litechess_monolith;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaRepositories
@EnableMongoRepositories
public class LitechessMonolithApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LitechessMonolithApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

	}
}

package com.aws.sqs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SqsPuplisherApplication {

	public static void main(String[] args) {
		SpringApplication.run(SqsPuplisherApplication.class, args);
	}

}

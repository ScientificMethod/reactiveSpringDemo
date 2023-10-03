package com.example.javadynamo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestJavaDynamoApplication {

	public static void main(String[] args) {
		SpringApplication.from(JavaDynamoApplication::main).with(TestJavaDynamoApplication.class).run(args);
	}

}

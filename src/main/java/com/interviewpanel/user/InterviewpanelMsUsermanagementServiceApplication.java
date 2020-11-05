package com.interviewpanel.user;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
@EnableEurekaClient
@SpringBootApplication
@EnableBinding(Source.class)
public class InterviewpanelMsUsermanagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterviewpanelMsUsermanagementServiceApplication.class, args);
	}

}

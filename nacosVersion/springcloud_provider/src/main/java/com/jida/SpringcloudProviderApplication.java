package com.jida;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableDiscoveryClient
@SpringBootApplication
@EnableScheduling
@EnableCaching
public class SpringcloudProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudProviderApplication.class, args);
	}
}

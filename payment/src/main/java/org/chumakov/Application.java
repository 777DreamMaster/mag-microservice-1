package org.chumakov;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@Slf4j
public class Application {

	private static String appVersion;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		log.info("App version: {}", appVersion);
	}

	@Value("${application.version}")
	private synchronized void setVersion(String version){
		appVersion = version;
	}
}

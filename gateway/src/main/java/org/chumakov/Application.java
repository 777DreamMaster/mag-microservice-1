package org.chumakov;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@SpringBootApplication
public class Application {

    private static String appVersion;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("App version: {}", appVersion);
    }

    @Value("${application.version}")
    private synchronized void setVersion(String version) {
        appVersion = version;
    }
}

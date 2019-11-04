package com.example.memberapi;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MemberApiApplication {
    private static final String PROPERTIES_LOCATIONS = "spring.config.location="
            + "classpath:/application.yml,"
            + "classpath:/aws.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(MemberApiApplication.class)
                .properties(PROPERTIES_LOCATIONS)
                .run(args);
    }

}

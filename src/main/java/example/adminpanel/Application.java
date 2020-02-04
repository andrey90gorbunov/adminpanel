/*
 * Copyright (c) Andrey Gorbunov
 */

package example.adminpanel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import example.adminpanel.admin.config.AdminConfig;
import example.adminpanel.core.config.CoreConfig;
import example.adminpanel.core.config.MongoConfig;

@SpringBootApplication
@Import(value = {CoreConfig.class, AdminConfig.class, MongoConfig.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

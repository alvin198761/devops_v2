package org.alvin.opsdev.appstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by tangzhichao on 2017/4/17.
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
//@ComponentScan("org.alvin.opsdev.oa")
//@EnableJpaRepositories("org.alvin.opsdev.oa.repository")
//@EntityScan("org.alvin.opsdev.oa.domain")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}


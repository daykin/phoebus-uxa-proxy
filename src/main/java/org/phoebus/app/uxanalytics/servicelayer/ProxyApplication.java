package org.phoebus.app.uxanalytics.servicelayer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.annotation.ApplicationScope;

@SpringBootApplication
@ApplicationScope
@ComponentScan(basePackages = "org.phoebus.app.uxanalytics.servicelayer.navigation")
@ComponentScan(basePackages = "org.phoebus.app.uxanalytics.servicelayer.clicktracking")
@ComponentScan(basePackages = "org.phoebus.app.uxanalytics.servicelayer.global")
public class ProxyApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProxyApplication.class, args);
    }
}

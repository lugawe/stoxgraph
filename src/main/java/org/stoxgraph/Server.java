package org.stoxgraph;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class Server {

    public Server() {}

    static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }
}

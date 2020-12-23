package org.nee.ny.sip.nysipserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Slf4j
public class NySipServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NySipServerApplication.class, args);
    }

}

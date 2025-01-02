package com.nxhu.foroHub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ForoHubApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(ForoHubApplication.class, args);
    }
}

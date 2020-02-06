package com.vitily.order.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StartupController implements CommandLineRunner {
    @Override
    public void run(String... args){
        try {
            log.info(" >>>>>>>>>>>>>  <<<<<<<<<<<<< ");
        }catch (Exception e){
            e.printStackTrace();
        }
    }















}

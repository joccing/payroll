package com.example.payroll.Repo;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableEncryptableProperties
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository) {

        repository.flush();

        return args -> log.info("Database ready.");
        /*
        return args -> {
            log.info("Preloading " + repository.save(new Employee("Bilbo Baggins", 500.0F)));
            log.info("Preloading " + repository.save(new Employee("Frodo Baggins", 670.0F)));
            log.info("Preloading " + repository.save(new Employee("Chris Evans", 300.50F)));
            log.info("Preloading " + repository.save(new Employee("Tony Stark", 1000.2F)));
            log.info("Preloading " + repository.save(new Employee("Phil Collins", 420.86F)));
            log.info("Preloading " + repository.save(new Employee("Johnny Cash", 230.84F)));
        };*/
    }
}

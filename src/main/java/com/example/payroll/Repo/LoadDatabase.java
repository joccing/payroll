package com.example.payroll.Repo;

import com.example.payroll.Models.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository) {

        long records = repository.count();
        if( records > 0 ) {
            repository.flush();
            return args -> log.info("Database loaded and ready: " + records + " records.");
        }else{
            return args -> {
                // if id is not reset
                // run "SET @reset = 0; UPDATE YOUR_TABLE_NAME SET id = @reset:= @reset + 1;"
                log.info("Preloading "+ repository.save(new Employee("Brayan Hayes",6593.7f)));
                log.info("Preloading "+ repository.save(new Employee("Cynthia Burgess",9230.5f)));
                log.info("Preloading "+ repository.save(new Employee("Tomas Gregory",7132.6f)));
                log.info("Preloading "+ repository.save(new Employee("Shyann Hendrix",1534.2f)));
                log.info("Preloading "+ repository.save(new Employee("Harry Levy",5243.4f)));
                log.info("Preloading "+ repository.save(new Employee("Britney Davis",4600.2f)));
                log.info("Preloading "+ repository.save(new Employee("Madelynn Bean",7394.6f)));
                log.info("Preloading "+ repository.save(new Employee("Camron Miller",96.8f)));
                log.info("Preloading "+ repository.save(new Employee("Brynlee Macdonald",8595.2f)));
                log.info("Preloading "+ repository.save(new Employee("Owen Bernard",2253f)));
                log.info("Preloading "+ repository.save(new Employee("Johnathan Weeks",6283f)));
                log.info("Preloading "+ repository.save(new Employee("Hector Peck",7732.3f)));
                log.info("Preloading "+ repository.save(new Employee("Rishi Barron",9014.6f)));
                log.info("Preloading "+ repository.save(new Employee("Bryce Craig",8064.6f)));
                log.info("Preloading "+ repository.save(new Employee("Jared Choi",4751.9f)));
                log.info("Preloading "+ repository.save(new Employee("Gordon Hart",7296f)));
                log.info("Preloading "+ repository.save(new Employee("Alfred Brady",2264.4f)));
            };
        }
    }
}

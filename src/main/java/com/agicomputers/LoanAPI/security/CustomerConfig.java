/*package com.agicomputers.LoanAPI.config;

import com.agicomputers.LoanAPI.models.entities.Customer;
import com.agicomputers.LoanAPI.repositories.user_repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfig {

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepo){
        return args ->{
            Customer star = new Customer();
            customerRepo.save(star);
        };
    }

}
*/
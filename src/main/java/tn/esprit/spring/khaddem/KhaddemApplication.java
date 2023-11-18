package tn.esprit.spring.khaddem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication
@EnableScheduling
@EntityScan("tn.esprit.spring.khaddem.entities")
public class KhaddemApplication {


    public static void main(String[] args) {
        SpringApplication.run(KhaddemApplication.class, args);
    }

}

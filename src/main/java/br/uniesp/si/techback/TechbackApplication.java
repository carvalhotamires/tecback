package br.uniesp.si.techback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("br.uniesp.si.techback.model")
@EnableJpaRepositories("br.uniesp.si.techback.repository")
@ComponentScan(basePackages = "br.uniesp.si.techback")
public class TechbackApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechbackApplication.class, args);
	}

}

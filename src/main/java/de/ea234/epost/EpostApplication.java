package de.ea234.epost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
//@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@SpringBootApplication(exclude = { org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration.class })
public class EpostApplication {

	public static void main(String[] args) {
		SpringApplication.run(EpostApplication.class, args);
	}

}

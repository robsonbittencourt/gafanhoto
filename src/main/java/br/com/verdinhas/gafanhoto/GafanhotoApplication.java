package br.com.verdinhas.gafanhoto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GafanhotoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GafanhotoApplication.class, args);
	}

}

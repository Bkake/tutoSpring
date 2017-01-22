package fr.free.bkake.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BusinessLuncher {

	private static final Logger log = LoggerFactory.getLogger(BusinessLuncher.class);

	public static void main(String[] args) {
		SpringApplication.run(BusinessLuncher.class);
	}
}



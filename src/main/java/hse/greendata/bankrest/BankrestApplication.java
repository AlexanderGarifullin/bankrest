package hse.greendata.bankrest;

import hse.greendata.bankrest.util.exceptions.ErrorMessagesBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BankrestApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankrestApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public ErrorMessagesBuilder errorMessagesBuilder(){return new ErrorMessagesBuilder();};
}

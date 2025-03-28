package pl.edu.wszib.account_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import pl.edu.wszib.account_service.entity.Account;
import pl.edu.wszib.account_service.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner init(AccountRepository repository){
		Account account1 = new Account();
		account1.setId(UUID.fromString("11111111-1111-1111-1111-111111111111"));
		account1.setEmeraldBalance(BigDecimal.valueOf(1000L));

		Account account2 = new Account();
		account2.setId(UUID.fromString("11111111-1111-1111-1111-222222222222"));
		account2.setEmeraldBalance(BigDecimal.valueOf(2000L));

		return args -> {
			repository.save(account1);
			repository.save(account2);
		};
	}

}

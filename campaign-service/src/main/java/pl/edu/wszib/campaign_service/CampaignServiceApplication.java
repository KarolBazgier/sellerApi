package pl.edu.wszib.campaign_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import pl.edu.wszib.campaign_service.entity.Campaign;

import pl.edu.wszib.campaign_service.repository.CampaignRepository;
import pl.edu.wszib.dto.Keywords;
import pl.edu.wszib.dto.Status;
import pl.edu.wszib.dto.Town;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class CampaignServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampaignServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner init(CampaignRepository repository){
		Campaign campaign = new Campaign();
		campaign.setId(UUID.randomUUID());
		campaign.setAccountId(UUID.fromString("11111111-1111-1111-1111-111111111111"));
		campaign.setCampaignName("Test campaign 1 ");
		List<Keywords> keywords = new ArrayList<>();
		keywords.add(Keywords.SPORT);
		keywords.add(Keywords.FISHING);
		campaign.setKeywords(keywords);
		campaign.setBidAmount(BigDecimal.ONE);
		campaign.setCampaignFund(BigDecimal.valueOf(500l));
		campaign.setStatus(Status.ON);
		campaign.setTown(Town.KRAKÓW);
		campaign.setRadiusKM(50);


		Campaign campaign2 = new Campaign();
		campaign2.setId(UUID.randomUUID());
		campaign2.setAccountId(UUID.fromString("11111111-1111-1111-1111-222222222222"));
		campaign2.setCampaignName("Test campaign 2 ");
		List<Keywords> keywords2 = new ArrayList<>();
		keywords2.add(Keywords.HOME);
		keywords2.add(Keywords.BATHROOM);
		campaign2.setKeywords(keywords);
		campaign2.setBidAmount(BigDecimal.TEN);
		campaign2.setCampaignFund(BigDecimal.valueOf(300l));
		campaign2.setStatus(Status.ON);
		campaign2.setTown(Town.KRAKÓW);
		campaign2.setRadiusKM(50);

		return args -> {
			repository.save(campaign);
			repository.save(campaign2);
		};
	}


}

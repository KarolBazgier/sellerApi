package pl.edu.wszib.gateway_api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "campaign-service")
public interface CampaignClient {
    @GetMapping("/campaign")
    public List<Object> getAllCampaigns();
}

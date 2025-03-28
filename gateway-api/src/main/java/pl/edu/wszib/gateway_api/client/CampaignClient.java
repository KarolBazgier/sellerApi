package pl.edu.wszib.gateway_api.client;

import jakarta.ws.rs.Path;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.edu.wszib.gateway_api.dto.CampaignDTO;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "campaign-service", configuration = FeignFormSupportConfig.class)
public interface CampaignClient {
    @GetMapping("/campaign")
    public List<CampaignDTO> getAllCampaigns();

    @GetMapping("/campaign/{accountId}")
    public List<CampaignDTO> getAccountCampaigns(@PathVariable UUID accountId);

    @GetMapping("/campaign/{accountId}/otherCampaigns")
    public List<CampaignDTO> getNotAccountCampaigns(@PathVariable UUID accountId);

    @PostMapping("/campaign/change-status/{campaignId}")
    public CampaignDTO changeStatus(@PathVariable UUID campaignId);

    @GetMapping("/campaign/get/{campaignId}")
    public CampaignDTO getCampaignById(@PathVariable UUID campaignId);

    @PostMapping(value = "/campaign/edit/{campaignId}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    CampaignDTO editCampaign(@PathVariable UUID campaignId, @ModelAttribute CampaignDTO campaign);

    @PostMapping(value = "/campaign/new", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public CampaignDTO newCampaign(@ModelAttribute CampaignDTO campaign);

    @PostMapping("/campaign/delete/{campaignId}")
    public void deleteCampaign(@PathVariable UUID campaignId);

}

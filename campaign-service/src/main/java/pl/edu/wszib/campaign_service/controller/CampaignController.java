package pl.edu.wszib.campaign_service.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wszib.campaign_service.dto.CreateCampaignRequest;
import pl.edu.wszib.campaign_service.entity.Campaign;
import pl.edu.wszib.campaign_service.service.CampaignService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/campaign")
public class CampaignController {
    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping
    public ResponseEntity<List<Campaign>> getAllCampaigns(){
        return ResponseEntity.ok(campaignService.getAllCampaigns());
    }

    @GetMapping("/{campaignId}")
    public ResponseEntity<Campaign> getCampaignById(@PathVariable UUID campaignId){
       return ResponseEntity.ok(campaignService.getCampaignById(campaignId));
    }

    @PostMapping("/new")
    public ResponseEntity<Campaign> createCampaign(@RequestBody @Valid CreateCampaignRequest createCampaignRequest){
        Campaign campaign = campaignService.createCampaign(createCampaignRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(campaign);
    }

}

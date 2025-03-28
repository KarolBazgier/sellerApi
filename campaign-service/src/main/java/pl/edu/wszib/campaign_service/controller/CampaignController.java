package pl.edu.wszib.campaign_service.controller;

import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
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

    @GetMapping("/{accountId}/otherCampaigns")
    public ResponseEntity<List<Campaign>> getNotAccountCampaigns(@PathVariable UUID accountId){
        return ResponseEntity.ok(campaignService.getOtherCampaigns(accountId));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<List<Campaign>> getAccountCampaigns(@PathVariable UUID accountId){
        return ResponseEntity.ok(campaignService.getAccountCampaigns(accountId));
    }

    @PostMapping("/change-status/{campaignId}")
    public ResponseEntity<Campaign> changeStatus(@PathVariable UUID campaignId){
        return ResponseEntity.ok(campaignService.changeStatus(campaignId));
    }

    @PostMapping("/new")
    public ResponseEntity<Campaign> createCampaign(@ModelAttribute CreateCampaignRequest createCampaignRequest){
        Campaign campaign = campaignService.createCampaign(createCampaignRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(campaign);
    }
    @PostMapping("/edit/{campaignId}")
    public ResponseEntity<Campaign> editCampaign(@PathVariable UUID campaignId, @ModelAttribute CreateCampaignRequest createCampaignRequest){
        return ResponseEntity.ok(campaignService.updateCampaign(campaignId, createCampaignRequest));
    }

    @GetMapping("/get/{campaignId}")
    public ResponseEntity<Campaign> getCampaignById(@PathVariable UUID campaignId){
        return ResponseEntity.ok(campaignService.getCampaignById(campaignId));
    }

    @PostMapping("/delete/{campaignId}")
    public void deleteCampaign(@PathVariable UUID campaignId){
        campaignService.deleteCampaign(campaignId);
    }

}

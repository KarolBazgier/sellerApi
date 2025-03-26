package pl.edu.wszib.campaign_service.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import pl.edu.wszib.campaign_service.client.AccountClient;
import pl.edu.wszib.campaign_service.dto.CreateCampaignRequest;
import pl.edu.wszib.campaign_service.dto.DeductRequest;
import pl.edu.wszib.campaign_service.entity.Campaign;
import pl.edu.wszib.campaign_service.repository.CampaignRepository;

import java.util.List;
import java.util.UUID;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final AccountClient accountClient;

    public CampaignService(CampaignRepository campaignRepository, AccountClient accountClient) {
        this.campaignRepository = campaignRepository;
        this.accountClient = accountClient;
    }

    public Campaign createCampaign(CreateCampaignRequest createCampaignRequest){
        DeductRequest deductRequest = new DeductRequest();
        deductRequest.setAmount(createCampaignRequest.getCampaignFund());
        UUID accountId = createCampaignRequest.getAccountId();
        accountClient.deductFunds(accountId, deductRequest);

        Campaign campaign = new Campaign();
        campaign.setId(UUID.randomUUID());
        campaign.setSellerId(accountId);
        campaign.setCampaignName(createCampaignRequest.getCampaignName());
        campaign.setKeywords(createCampaignRequest.getKeywords());
        campaign.setBidAmount(createCampaignRequest.getBidAmount());
        campaign.setCampaignFund(createCampaignRequest.getCampaignFund());
        campaign.setStatus(createCampaignRequest.getStatus());
        campaign.setTown(createCampaignRequest.getTown());
        campaign.setRadiusKM(createCampaignRequest.getRadiusKM());

        return campaignRepository.save(campaign);
    }

    public List<Campaign> getAllCampaigns(){
        return campaignRepository.findAll();
    }

    public Campaign getCampaignById(UUID campaignId){
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new EntityNotFoundException("Campaign not found with id: " + campaignId));
        return campaign;
    }

    public Campaign updateCampaign(UUID campaignId, Campaign updatedCampaign){
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new EntityNotFoundException("Campaign not found with id: " + campaignId));

        BeanUtils.copyProperties(updatedCampaign, campaign, "id");

        return campaignRepository.save(campaign);
    }

    public void deleteCampaign(UUID campaignId){
        campaignRepository.deleteById(campaignId);
    }
}

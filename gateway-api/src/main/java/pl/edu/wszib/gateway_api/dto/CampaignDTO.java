package pl.edu.wszib.gateway_api.dto;

import ch.qos.logback.core.status.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@Data
public class CampaignDTO {
    private UUID id;
    private String campaignName;
    private BigDecimal campaignFund;
    private BigDecimal bidAmount;
    private Status status;
    private String town;
    private int radiusKM;
    private List<String> keywords;
}

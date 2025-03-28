package pl.edu.wszib.gateway_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignDTO {
    private UUID id;
    private UUID accountId;
    private String campaignName;
    private BigDecimal campaignFund;
    private BigDecimal bidAmount;
    private Status status;
    private Town town;
    private int radiusKM;
    private List<Keywords> keywords;
}

package pl.edu.wszib.campaign_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pl.edu.wszib.campaign_service.entity.Keywords;
import pl.edu.wszib.campaign_service.entity.Status;
import pl.edu.wszib.campaign_service.entity.Town;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateCampaignRequest {
    @NotNull
    private UUID accountId;

    @NotBlank
    private String campaignName;

    @NotEmpty
    private List<Keywords> keywords;

    @DecimalMin("0.01")
    private BigDecimal bidAmount;

    @DecimalMin("100.00")
    private BigDecimal campaignFund;

    @NotBlank
    private Status status;

    @NotBlank
    private Town town;

    @NotBlank
    private int radiusKM;
}

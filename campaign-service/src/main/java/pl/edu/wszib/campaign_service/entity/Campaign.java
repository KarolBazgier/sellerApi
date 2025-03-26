package pl.edu.wszib.campaign_service.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.internal.build.AllowNonPortable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Campaign {
    @Id
    private UUID id;

    private UUID sellerId;

    private String campaignName;

    @ElementCollection
    private List<Keywords> keywords;

    private BigDecimal bidAmount;

    private BigDecimal campaignFund;

    private Status status;

    private Town town;

    private int radiusKM;


}

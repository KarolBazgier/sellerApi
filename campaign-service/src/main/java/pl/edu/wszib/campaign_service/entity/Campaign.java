package pl.edu.wszib.campaign_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.internal.build.AllowNonPortable;
import pl.edu.wszib.dto.Keywords;
import pl.edu.wszib.dto.Status;
import pl.edu.wszib.dto.Town;

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

    private UUID accountId;

    private String campaignName;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Keywords> keywords;

    private BigDecimal bidAmount;

    private BigDecimal campaignFund;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Town town;

    private int radiusKM;


}

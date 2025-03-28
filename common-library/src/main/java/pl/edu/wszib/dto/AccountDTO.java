package pl.edu.wszib.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AccountDTO {
    private UUID id;
    private BigDecimal emeraldBalance;
}

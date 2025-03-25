package pl.edu.wszib.account_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DeductRequest {
    private BigDecimal amount;
}

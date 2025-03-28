package pl.edu.wszib.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DeductRequest {
    private BigDecimal amount;
}

package pl.edu.wszib.account_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.edu.wszib.account_service.entity.Account;
import pl.edu.wszib.account_service.repository.AccountRepository;
import pl.edu.wszib.dto.DeductRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 8092)
@AutoConfigureMockMvc
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountRepository accountRepository;

    private UUID accountId = UUID.fromString("11111111-1111-1111-1111-111111111111");

    @BeforeEach
    void setup() {
        accountRepository.findById(accountId).orElseGet(() -> {
            Account account = new Account();
            account.setId(accountId);
            account.setEmeraldBalance(new BigDecimal("1000.00"));
            return accountRepository.save(account);
        });
    }

    @Test
    void shouldReturnAccountBalance() throws Exception{
        mockMvc.perform(get("/account/" + accountId +"/balance"))
                .andExpect(status().isOk())
                .andExpect(content().string("1000.00"));
    }

    @Test
    void shouldDeductFundsSuccessfully() throws Exception{
        DeductRequest deductRequest = new DeductRequest();
        deductRequest.setAmount(new BigDecimal("100.00"));

        mockMvc.perform(post("/account/" +accountId+"/deduct")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deductRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("900.00"));
    }

    @Test
    void shouldRejectTransactionWhenInsufficientFunds() throws Exception{
        DeductRequest deductRequest = new DeductRequest();
        deductRequest.setAmount(new BigDecimal("2000"));

        mockMvc.perform(post("/account/" + accountId +"/deduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deductRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Insufficient funds on account: "+ accountId));
    }

}

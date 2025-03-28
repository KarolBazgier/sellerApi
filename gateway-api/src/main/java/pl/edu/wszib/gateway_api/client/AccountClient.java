package pl.edu.wszib.gateway_api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.edu.wszib.dto.AccountDTO;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "account-service")
public interface AccountClient {

    @GetMapping("/account")
    public List<AccountDTO> getAllAccounts();

    @GetMapping("/account/{accountId}")
    public  AccountDTO getAccountById(@PathVariable UUID accountId);
}

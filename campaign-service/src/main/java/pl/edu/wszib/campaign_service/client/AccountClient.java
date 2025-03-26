package pl.edu.wszib.campaign_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.wszib.campaign_service.dto.DeductRequest;

import java.util.UUID;

@FeignClient(name = "account-service")
public interface AccountClient {

    @PostMapping("/account/{id}/deduct")
    void deductFunds(@PathVariable("id") UUID accountId, @RequestParam("amount")DeductRequest deductRequest);
}

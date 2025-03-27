package pl.edu.wszib.campaign_service.client;

import lombok.Getter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import pl.edu.wszib.campaign_service.dto.DeductRequest;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "account-service")
public interface AccountClient {

    @PostMapping("/account/{accountId}/deduct")
    void deductFunds(@PathVariable UUID accountId, @RequestBody DeductRequest deductRequest);


}

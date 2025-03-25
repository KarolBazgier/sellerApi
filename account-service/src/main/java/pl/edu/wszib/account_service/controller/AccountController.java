package pl.edu.wszib.account_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wszib.account_service.dto.DeductRequest;
import pl.edu.wszib.account_service.service.AccountService;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable UUID accountId){
        return ResponseEntity.ok(accountService.getBalance(accountId));
    }

    @PostMapping("/{accountId}/deduct")
    public ResponseEntity<?> deductFunds(@PathVariable UUID accountId, @RequestBody DeductRequest deductRequest){
        try {
            BigDecimal newBalance = accountService.deductFunds(accountId, deductRequest.getAmount());
            return ResponseEntity.ok(newBalance);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

package pl.edu.wszib.account_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wszib.account_service.entity.Account;
import pl.edu.wszib.account_service.service.AccountService;
import pl.edu.wszib.dto.DeductRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts(){
        return ResponseEntity.ok(accountService.getAll());
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable UUID accountId){
        return ResponseEntity.ok(accountService.getAccountById(accountId));
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

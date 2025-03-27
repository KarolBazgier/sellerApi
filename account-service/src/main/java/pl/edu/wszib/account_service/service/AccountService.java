package pl.edu.wszib.account_service.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.wszib.account_service.entity.Account;
import pl.edu.wszib.account_service.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public BigDecimal getBalance(UUID accountId){
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"))
                .getEmeraldBalance();
    }

    public BigDecimal deductFunds(UUID accountId, BigDecimal amount){
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account dont found"));

        if (account.getEmeraldBalance().compareTo(amount) < 0){
            throw new IllegalArgumentException("Insufficient funds on account: " + accountId);
        }

        BigDecimal newBalance = account.getEmeraldBalance().subtract(amount);
        account.setEmeraldBalance(newBalance);
        accountRepository.save(account);

        return newBalance;
    }

    public Account createOrUpdateAccount(Account account){
        return accountRepository.save(account);
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public Account getAccountById(UUID accountId) {
       Account account = accountRepository.findById(accountId)
               .orElseThrow(() -> new EntityNotFoundException("USer with id: " + accountId + " not found"));
       return account;
    }
}

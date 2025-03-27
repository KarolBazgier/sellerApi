package pl.edu.wszib.gateway_api.service;

import org.springframework.stereotype.Service;
import pl.edu.wszib.gateway_api.client.AccountClient;
import pl.edu.wszib.gateway_api.dto.AccountDTO;

import java.util.List;
import java.util.UUID;

@Service
public class AccountClientService {

    private final AccountClient accountClient;

    public AccountClientService(AccountClient accountClient) {
        this.accountClient = accountClient;
    }

    public List<AccountDTO> accounts(){

        try {
            List<AccountDTO> accountsList =  accountClient.getAllAccounts();
            return accountsList;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }

    public AccountDTO getAccountById(UUID id) {
        try {
            AccountDTO accountDTO = accountClient.getAccountById(id);
            return accountDTO;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}

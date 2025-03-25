package pl.edu.wszib.account_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wszib.account_service.entity.Account;

import java.util.UUID;
@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
}

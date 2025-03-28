package pl.edu.wszib.campaign_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wszib.campaign_service.entity.Campaign;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, UUID> {
    public Optional<Campaign> findById(UUID id);

    List<Campaign> findAllByAccountId(UUID accountId);

    List<Campaign> findAllByAccountIdNot(UUID accountId);
}

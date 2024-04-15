package hse.greendata.bankrest.repositories;

import hse.greendata.bankrest.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByName(String name);

    Optional<Client> findByShortName(String shortName);
}

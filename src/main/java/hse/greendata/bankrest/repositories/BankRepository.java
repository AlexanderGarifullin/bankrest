package hse.greendata.bankrest.repositories;

import hse.greendata.bankrest.models.Bank;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank, Integer> {
    Optional<Bank> findByName(String name);

    Optional<Bank> findByBik(String bik);
    List<Bank> findAll(Sort sort);
}

package hse.greendata.bankrest.repositories;

import hse.greendata.bankrest.models.Deposit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Integer> {
    List<Deposit> findAll(Sort sort);
}

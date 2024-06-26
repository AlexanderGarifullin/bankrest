package hse.greendata.bankrest.repositories;

import hse.greendata.bankrest.models.OrganizationalLegalForm;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationalLegalFormRepository extends JpaRepository<OrganizationalLegalForm, Integer> {
    Optional<OrganizationalLegalForm> findByName(String name);

    List<OrganizationalLegalForm> findAll(Sort sort);

}

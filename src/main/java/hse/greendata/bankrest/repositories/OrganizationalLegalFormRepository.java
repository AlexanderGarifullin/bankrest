package hse.greendata.bankrest.repositories;

import hse.greendata.bankrest.models.OrganizationalLegalForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationalLegalFormRepository extends JpaRepository<OrganizationalLegalForm, Integer> {
}

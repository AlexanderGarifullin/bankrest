package hse.greendata.bankrest.services;

import hse.greendata.bankrest.models.OrganizationalLegalForm;
import hse.greendata.bankrest.repositories.OrganizationalLegalFormRepository;
import hse.greendata.bankrest.util.OrganizationalLegalForm.exception.OrganizationalLegalFormNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrganizationalLegalFormService {

    private final OrganizationalLegalFormRepository organizationalLegalFormRepository;

    @Autowired
    public OrganizationalLegalFormService(OrganizationalLegalFormRepository organizationalLegalFormRepository) {
        this.organizationalLegalFormRepository = organizationalLegalFormRepository;
    }

    public List<OrganizationalLegalForm> findAll() {
        return organizationalLegalFormRepository.findAll();
    }

    public OrganizationalLegalForm findOne(int id) {
        Optional<OrganizationalLegalForm> foundOrganizationalLegalForm = organizationalLegalFormRepository.findById(id);
        return foundOrganizationalLegalForm.orElseThrow(OrganizationalLegalFormNotFoundException::new);
    }

    @Transactional
    public void save(OrganizationalLegalForm person) {
        organizationalLegalFormRepository.save(person);
    }

    @Transactional
    public void update(int id, OrganizationalLegalForm updatedOrganizationalLegalForm) {
        updatedOrganizationalLegalForm.setId(id);
        organizationalLegalFormRepository.save(updatedOrganizationalLegalForm);
    }

    @Transactional
    public void delete(int id) {
        organizationalLegalFormRepository.deleteById(id);
    }
}

package hse.greendata.bankrest.services;

import hse.greendata.bankrest.models.OrganizationalLegalForm;
import hse.greendata.bankrest.repositories.OrganizationalLegalFormRepository;
import hse.greendata.bankrest.util.exceptions.OrganizationalLegalForm.OrganizationalLegalFormIllegalSortArgument;
import hse.greendata.bankrest.util.exceptions.OrganizationalLegalForm.OrganizationalLegalFormNotFoundException;
import hse.greendata.bankrest.util.reflections.ReflectionFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    public List<OrganizationalLegalForm> findAll(String sort) {
        if (ReflectionFields.hasField(OrganizationalLegalForm.class, sort))  {
            return organizationalLegalFormRepository.findAll(Sort.by(sort));
        }
        throw new OrganizationalLegalFormIllegalSortArgument("Field " + sort + " does not exist in OrganizationalLegalForm class");
    }

    public OrganizationalLegalForm findOne(int id) {
        Optional<OrganizationalLegalForm> foundOrganizationalLegalForm = organizationalLegalFormRepository.findById(id);
        return foundOrganizationalLegalForm.orElseThrow(() ->
                new OrganizationalLegalFormNotFoundException("Organizational legal form with id " + id + " not found"));
    }

    public Optional<OrganizationalLegalForm> findOne(String name) {
        return organizationalLegalFormRepository.findByName(name);
    }

    public Optional<OrganizationalLegalForm> findById(int id) {
        return organizationalLegalFormRepository.findById(id);
    }

    @Transactional
    public void save(OrganizationalLegalForm organizationalLegalForm) {
        organizationalLegalFormRepository.save(organizationalLegalForm);
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

package hse.greendata.bankrest.services;

import hse.greendata.bankrest.models.Bank;;
import hse.greendata.bankrest.repositories.BankRepository;
import hse.greendata.bankrest.util.exceptions.Bank.BankIllegalSortArgument;
import hse.greendata.bankrest.util.exceptions.Bank.BankNotFoundException;
import hse.greendata.bankrest.util.reflections.ReflectionFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BankService {

    private final BankRepository bankRepository;

    @Autowired
    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public List<Bank> findAll(String sort) {
        if (ReflectionFields.hasField(Bank.class, sort))  {
            return bankRepository.findAll(Sort.by(sort));
        }
        throw new BankIllegalSortArgument("Field " + sort + " does not exist in OrganizationalLegalForm class");
    }

    public Bank findOne(int id) {
        Optional<Bank> foundBank = bankRepository.findById(id);
        return foundBank.orElseThrow(() ->
                new BankNotFoundException("Bank with id " + id + " not found"));
    }

    public Optional<Bank> findOneByName(String name) {
        return bankRepository.findByName(name);
    }

    public Optional<Bank> findOneByBik(String bik) {
        return bankRepository.findByBik(bik);
    }

    public Optional<Bank> findOneById(int id) {
        return bankRepository.findById(id);
    }

    @Transactional
    public void save(Bank bank) {
        bankRepository.save(bank);
    }

    @Transactional
    public void update(int id, Bank updatedBank) {
        updatedBank.setId(id);
        bankRepository.save(updatedBank);
    }

    @Transactional
    public void delete(int id) {
        bankRepository.deleteById(id);
    }
}

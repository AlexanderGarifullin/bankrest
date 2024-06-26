package hse.greendata.bankrest.services;

import hse.greendata.bankrest.models.Client;
import hse.greendata.bankrest.models.Deposit;
import hse.greendata.bankrest.repositories.DepositRepository;
import hse.greendata.bankrest.util.exceptions.Deposit.DepositIllegalSortArgument;
import hse.greendata.bankrest.util.exceptions.Deposit.DepositNotFoundException;
import hse.greendata.bankrest.util.reflections.ReflectionFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DepositService {

    private final DepositRepository depositRepository;

    @Autowired
    public DepositService(DepositRepository depositRepository) {
        this.depositRepository = depositRepository;
    }

    public List<Deposit> findAll(String sort) {
        if (ReflectionFields.hasField(Deposit.class, sort)) {
            return depositRepository.findAll(Sort.by(sort));
        }
        throw new DepositIllegalSortArgument("Field " + sort + " does not exist in OrganizationalLegalForm class");
    }
    public Deposit findOne(int id) {
        Optional<Deposit> foundDeposit = depositRepository.findById(id);
        return foundDeposit.orElseThrow(() ->
                new DepositNotFoundException("Client with id " + id + " not found"));
    }

    @Transactional
    public void save(Deposit deposit) {
        depositRepository.save(deposit);
    }

    @Transactional
    public void update(int id, Deposit updatedDeposit) {
        updatedDeposit.setId(id);
        depositRepository.save(updatedDeposit);
    }

    @Transactional
    public void delete(int id) {
        depositRepository.deleteById(id);
    }
}

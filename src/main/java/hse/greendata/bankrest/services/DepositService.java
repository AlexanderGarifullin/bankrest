package hse.greendata.bankrest.services;

import hse.greendata.bankrest.models.Client;
import hse.greendata.bankrest.models.Deposit;
import hse.greendata.bankrest.repositories.DepositRepository;
import hse.greendata.bankrest.util.exceptions.Client.ClientNotFoundException;
import hse.greendata.bankrest.util.exceptions.Deposit.DepositNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Deposit> findAll() {
        return depositRepository.findAll();
    }
    public Deposit findOne(int id) {
        Optional<Deposit> foundDeposit = depositRepository.findById(id);
        return foundDeposit.orElseThrow(() ->
                new DepositNotFoundException("Client with id " + id + " not found"));
    }
}

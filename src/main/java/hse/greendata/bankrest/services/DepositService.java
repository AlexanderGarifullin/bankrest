package hse.greendata.bankrest.services;

import hse.greendata.bankrest.models.Client;
import hse.greendata.bankrest.models.Deposit;
import hse.greendata.bankrest.repositories.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

}

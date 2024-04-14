package hse.greendata.bankrest.services;

import hse.greendata.bankrest.models.Bank;
import hse.greendata.bankrest.models.Client;
import hse.greendata.bankrest.repositories.ClientRepository;
import hse.greendata.bankrest.util.exceptions.Bank.BankNotFoundException;
import hse.greendata.bankrest.util.exceptions.Client.ClientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findOne(int id) {
        Optional<Client> foundClient = clientRepository.findById(id);
        return foundClient.orElseThrow(() ->
                new ClientNotFoundException("Client with id " + id + " not found"));
    }

}

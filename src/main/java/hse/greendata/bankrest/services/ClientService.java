package hse.greendata.bankrest.services;

import hse.greendata.bankrest.models.Bank;
import hse.greendata.bankrest.models.Client;
import hse.greendata.bankrest.repositories.ClientRepository;
import hse.greendata.bankrest.util.exceptions.Client.ClientIllegalSortArgument;
import hse.greendata.bankrest.util.exceptions.Client.ClientNotFoundException;
import hse.greendata.bankrest.util.reflections.ReflectionFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    public List<Client> findAll(String sort) {
        if (ReflectionFields.hasField(Client.class, sort)) {
            return clientRepository.findAll(Sort.by(sort));
        }
        throw new ClientIllegalSortArgument("Field " + sort + " does not exist in OrganizationalLegalForm class");
    }

    public Client findOne(int id) {
        Optional<Client> foundClient = clientRepository.findById(id);
        return foundClient.orElseThrow(() ->
                new ClientNotFoundException("Client with id " + id + " not found"));
    }

    public Optional<Client> findOneByName(String name) {
        return clientRepository.findByName(name);
    }

    public Optional<Client> findOneByShortName(String shortName) {
        return clientRepository.findByShortName(shortName);
    }

    public Optional<Client> findOneById(int id) {
        return clientRepository.findById(id);
    }

    @Transactional
    public void save(Client client) {
        clientRepository.save(client);
    }

    @Transactional
    public void update(int id, Client updatedClient) {
        updatedClient.setId(id);
        clientRepository.save(updatedClient);
    }

    @Transactional
    public void delete(int id) {
        clientRepository.deleteById(id);
    }
}

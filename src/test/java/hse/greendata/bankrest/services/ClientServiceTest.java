package hse.greendata.bankrest.services;

import hse.greendata.bankrest.models.Client;
import hse.greendata.bankrest.repositories.ClientRepository;
import hse.greendata.bankrest.util.exceptions.Client.ClientNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @Test
    void testFindOne_WhenClientExists_ReturnClient() {
        Client client = new Client();
        client.setId(1);
        client.setName("Client");
        client.setShortName("c");
        client.setAddress("Россия, Москва, 117312, ул. Тверская, д. 10");
        client.setOrganizationalLegalFormId(1);

        when(clientRepository.findById(1)).thenReturn(Optional.of(client));

        Client result = clientService.findOne(1);

        assertEquals(client.getId(), result.getId());
        assertEquals(client.getName(), result.getName());
        assertEquals(client.getShortName(), result.getShortName());
        assertEquals(client.getAddress(), result.getAddress());
        assertEquals(client.getOrganizationalLegalFormId(), result.getOrganizationalLegalFormId());

        verify(clientRepository).findById(1);
    }

    @Test
    void testFindOne_WhenClientNotExists_ThrowException() {
        when(clientRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> {
            clientService.findOne(1);
        });

        verify(clientRepository).findById(1);
    }

    @Test
    void testFindAll_WhenClientsExist_ReturnListOfClients() {
        Client client = new Client(1, "Client", "c",
                "Россия, Москва, 117312, ул. Тверская, д. 10", 1);
        List<Client> expectedClients = List.of(client);

        when(clientRepository.findAll()).thenReturn(expectedClients);

        List<Client> actualClients = clientService.findAll();

        assertEquals(expectedClients.size(), actualClients.size());
        for (int i = 0; i < expectedClients.size(); i++) {
            Client client1 = expectedClients.get(i);
            Client client2 = actualClients.get(i);
            assertEquals(client1.getId(), client2.getId());
            assertEquals(client1.getName(), client2.getName());
            assertEquals(client1.getShortName(), client2.getShortName());
            assertEquals(client1.getAddress(), client2.getAddress());
            assertEquals(client1.getOrganizationalLegalFormId(), client2.getOrganizationalLegalFormId());
        }

        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void testFindOneByName_WhenClientExist_ReturnClient() {
        Client client = new Client(1, "Client", "c",
                "Россия, Москва, 117312, ул. Тверская, д. 10", 1);
        when(clientRepository.findByName(client.getName())).thenReturn(Optional.of(client));

        Optional<Client> result = clientService.findOneByName(client.getName());

        assert(result.isPresent());
        Client client2 = result.orElse(null);
        assertEquals(client.getId(), client2.getId());
        assertEquals(client.getName(), client2.getName());
        assertEquals(client.getShortName(), client2.getShortName());
        assertEquals(client.getAddress(), client2.getAddress());
        assertEquals(client.getOrganizationalLegalFormId(), client2.getOrganizationalLegalFormId());

        verify(clientRepository, times(1)).findByName(client.getName());
    }

    @Test
    void testFindOneByShortName_WhenClientExist_ReturnClient() {
        Client client = new Client(1, "Client", "c",
                "Россия, Москва, 117312, ул. Тверская, д. 10", 1);
        when(clientRepository.findByShortName(client.getShortName())).thenReturn(Optional.of(client));

        Optional<Client> result = clientService.findOneByShortName(client.getShortName());

        assert(result.isPresent());
        Client client2 = result.orElse(null);
        assertEquals(client.getId(), client2.getId());
        assertEquals(client.getName(), client2.getName());
        assertEquals(client.getShortName(), client2.getShortName());
        assertEquals(client.getAddress(), client2.getAddress());
        assertEquals(client.getOrganizationalLegalFormId(), client2.getOrganizationalLegalFormId());

        verify(clientRepository, times(1)).findByShortName(client.getShortName());
    }

    @Test
    void testFindOneById_WhenClientExist_ReturnClient() {
        Client client = new Client(1, "Client", "c",
                "Россия, Москва, 117312, ул. Тверская, д. 10", 1);
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        Optional<Client> result = clientService.findOneById(client.getId());

        assert(result.isPresent());
        Client client2 = result.orElse(null);
        assertEquals(client.getId(), client2.getId());
        assertEquals(client.getName(), client2.getName());
        assertEquals(client.getShortName(), client2.getShortName());
        assertEquals(client.getAddress(), client2.getAddress());
        assertEquals(client.getOrganizationalLegalFormId(), client2.getOrganizationalLegalFormId());

        verify(clientRepository, times(1)).findById(client.getId());
    }

    @Test
    void testSave() {
        Client client = new Client(1, "Client", "c",
                "Россия, Москва, 117312, ул. Тверская, д. 10", 1);
        clientService.save(client);

        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void testUpdate() {
        int id = 1;
        Client client = new Client(2, "Client", "c",
                "Россия, Москва, 117312, ул. Тверская, д. 10", 1);
        clientService.update(id, client);

        verify(clientRepository, times(1)).save(client);

        assertEquals(id, client.getId());
    }

    @Test
    void testDelete() {
        int id = 1;

        clientService.delete(id);

        verify(clientRepository, times(1)).deleteById(id);
    }
}
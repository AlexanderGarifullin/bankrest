package hse.greendata.bankrest.services;

import hse.greendata.bankrest.models.Bank;
import hse.greendata.bankrest.models.Client;
import hse.greendata.bankrest.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
}
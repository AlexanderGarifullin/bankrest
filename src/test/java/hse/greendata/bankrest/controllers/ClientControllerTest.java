package hse.greendata.bankrest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import hse.greendata.bankrest.dto.BankDTO;
import hse.greendata.bankrest.dto.ClientDTO;
import hse.greendata.bankrest.models.Bank;
import hse.greendata.bankrest.models.Client;
import hse.greendata.bankrest.repositories.ClientRepository;
import hse.greendata.bankrest.repositories.OrganizationalLegalFormRepository;
import hse.greendata.bankrest.services.ClientService;
import hse.greendata.bankrest.services.OrganizationalLegalFormService;
import hse.greendata.bankrest.util.exceptions.ErrorMessagesBuilder;
import hse.greendata.bankrest.util.validators.ClientValidator;
import hse.greendata.bankrest.util.validators.OrganizationalLegalFormValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService service;

    @MockBean
    private ClientRepository repository;

    @MockBean
    private ClientValidator validator;

    @MockBean
    private OrganizationalLegalFormService serviceForm;

    @MockBean
    private OrganizationalLegalFormRepository repositoryForm;

    @MockBean
    private OrganizationalLegalFormValidator validatorForm;
    @MockBean
    private ErrorMessagesBuilder errorMessagesBuilder;



    @Test
    void testGetClients() throws Exception {
        Client client1 = new Client(1, "name1", "n1",
                "Россия, Москва, 117312, ул. Тверская, д. 10", 1);
        Client client2 = new Client(2, "name2", "n2",
                "Россия, Москва, 117312, ул. Тверская, д. 10", 1);

        List<Client> clients = Arrays.asList(client1, client2);
        when(service.findAll()).thenReturn(clients);


        mockMvc.perform(MockMvcRequestBuilders.get("/client")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("name1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].shortName").value("n1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address")
                        .value("Россия, Москва, 117312, ул. Тверская, д. 10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].organizationalLegalFormId")
                        .value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("name2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].shortName").value("n2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].address")
                        .value("Россия, Москва, 117312, ул. Тверская, д. 10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].organizationalLegalFormId")
                        .value(1));
    }

    @Test
    void testGetClientsById() throws Exception{
        Client client = new Client(1, "client1", "c1",
                "Россия, Москва, 117312, ул. Тверская, д. 10", 1);

        when(service.findOne(1)).thenReturn(client);


        mockMvc.perform(MockMvcRequestBuilders.get("/client/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                        .value("client1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.shortName")
                        .value("c1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address")
                        .value("Россия, Москва, 117312, ул. Тверская, д. 10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.organizationalLegalFormId")
                        .value(1));
    }

    @Test
    void testCreateClient() throws Exception {
        ClientDTO clientDTO = new ClientDTO();

        clientDTO.setName("client1");
        clientDTO.setShortName("c1");
        clientDTO.setAddress("Россия, Москва, 117312, ул. Тверская, д. 10");
        clientDTO.setOrganizationalLegalFormId(1);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(clientDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/client")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateClientWithEmptyName() throws Exception {
        ClientDTO clientDTO = new ClientDTO();

        clientDTO.setName("");
        clientDTO.setShortName("c1");
        clientDTO.setAddress("Россия, Москва, 117312, ул. Тверская, д. 10");
        clientDTO.setOrganizationalLegalFormId(1);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(clientDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/client")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateClient() throws Exception {
        int id = 1;
        ClientDTO clientDTO = new ClientDTO();

        clientDTO.setName("client");
        clientDTO.setShortName("c1");
        clientDTO.setAddress("Россия, Москва, 117312, ул. Тверская, д. 10");
        clientDTO.setOrganizationalLegalFormId(1);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(clientDTO);

        mockMvc.perform(MockMvcRequestBuilders.patch("/client/{id}", id)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateClientWithEmptyName() throws Exception {
        int id = 1;
        ClientDTO clientDTO = new ClientDTO();

        clientDTO.setName("");
        clientDTO.setShortName("c1");
        clientDTO.setAddress("Россия, Москва, 117312, ул. Тверская, д. 10");
        clientDTO.setOrganizationalLegalFormId(1);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(clientDTO);

        mockMvc.perform(MockMvcRequestBuilders.patch("/client/{id}", id)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
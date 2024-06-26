package hse.greendata.bankrest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import hse.greendata.bankrest.dto.DepositDTO;
import hse.greendata.bankrest.models.Deposit;
import hse.greendata.bankrest.repositories.BankRepository;
import hse.greendata.bankrest.repositories.ClientRepository;
import hse.greendata.bankrest.repositories.DepositRepository;
import hse.greendata.bankrest.services.BankService;
import hse.greendata.bankrest.services.ClientService;
import hse.greendata.bankrest.services.DepositService;
import hse.greendata.bankrest.util.validators.BankValidator;
import hse.greendata.bankrest.util.validators.ClientValidator;
import hse.greendata.bankrest.util.validators.DepositValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepositController.class)
class DepositControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepositService service;

    @MockBean
    private DepositRepository repository;

    @MockBean
    private DepositValidator validator;

    @MockBean
    private ClientService clientService;
    @MockBean
    private ClientRepository clientRepository;
    @MockBean
    private ClientValidator clientValidator;

    @MockBean
    private BankService bankService;
    @MockBean
    private BankRepository bankRepository;
    @MockBean
    private BankValidator bankValidator;

    @Test
    void testGetDeposits() throws Exception {
        Deposit deposit1 = new Deposit(1, 1 ,1, LocalDate.of(2020, 3, 20),
                10.0, 3);
        Deposit deposit2 = new Deposit(2, 1, 2, LocalDate.of(2023, 6, 10),
                5.2, 6);
        List<Deposit> deposits = Arrays.asList(deposit1, deposit2);
        when(service.findAll("id")).thenReturn(deposits);

        mockMvc.perform(MockMvcRequestBuilders.get("/deposit")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].clientId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bankId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].openingDate").value("2020-03-20"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].interestRate").value(10.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].termMonths").value(3))

                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].clientId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].bankId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].openingDate").value("2023-06-10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].interestRate").value(5.2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].termMonths").value(6));
    }

    @Test
    void testGetDepositsById() throws Exception{
        Deposit deposit = new Deposit(1, 1 ,1, LocalDate.of(2020, 3, 20),
                10.0, 3);
        when(service.findOne(1)).thenReturn(deposit);


        mockMvc.perform(MockMvcRequestBuilders.get("/deposit/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bankId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.openingDate").value("2020-03-20"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.interestRate").value(10.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.termMonths").value(3));
    }

    @Test
    void testCreateDeposit() throws Exception {
        DepositDTO depositDTO = new DepositDTO();

        depositDTO.setBankId(1);
        depositDTO.setClientId(1);
        depositDTO.setOpeningDate(LocalDate.of(2020, 3, 20));
        depositDTO.setInterestRate(15.2);
        depositDTO.setTermMonths(5);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        String requestBody = objectMapper.writeValueAsString(depositDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/deposit")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateDepositWithEmptyInterestRate() throws Exception {
        DepositDTO depositDTO = new DepositDTO();

        depositDTO.setBankId(1);
        depositDTO.setClientId(1);
        depositDTO.setOpeningDate(LocalDate.of(2020, 3, 20));
        depositDTO.setInterestRate(null);
        depositDTO.setTermMonths(5);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        String requestBody = objectMapper.writeValueAsString(depositDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/deposit")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateDeposit() throws Exception {
        int id = 1;
        DepositDTO depositDTO = new DepositDTO();
        depositDTO.setBankId(1);
        depositDTO.setClientId(1);
        depositDTO.setOpeningDate(LocalDate.of(2020, 3, 20));
        depositDTO.setInterestRate(4.8);
        depositDTO.setTermMonths(5);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        String requestBody = objectMapper.writeValueAsString(depositDTO);

        mockMvc.perform(MockMvcRequestBuilders.patch("/deposit/{id}", id)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateDepositWithEmptyTermMonth() throws Exception {
        int id = 1;
        DepositDTO depositDTO = new DepositDTO();
        depositDTO.setBankId(1);
        depositDTO.setClientId(1);
        depositDTO.setOpeningDate(LocalDate.of(2020, 3, 20));
        depositDTO.setInterestRate(4.8);
        depositDTO.setTermMonths(null);;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        String requestBody = objectMapper.writeValueAsString(depositDTO);

        mockMvc.perform(MockMvcRequestBuilders.patch("/deposit/{id}", id)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteDeposit() throws Exception{
        int idToDelete = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/deposit/{id}", idToDelete)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).delete(idToDelete);
    }
}
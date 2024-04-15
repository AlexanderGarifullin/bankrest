package hse.greendata.bankrest.controllers;

import hse.greendata.bankrest.models.Bank;
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
import static org.junit.jupiter.api.Assertions.*;
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
        when(service.findAll()).thenReturn(deposits);

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


}
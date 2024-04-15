package hse.greendata.bankrest.controllers;

import hse.greendata.bankrest.models.Bank;
import hse.greendata.bankrest.models.OrganizationalLegalForm;
import hse.greendata.bankrest.repositories.BankRepository;
import hse.greendata.bankrest.services.BankService;
import hse.greendata.bankrest.util.validators.BankValidator;
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

@WebMvcTest(BankController.class)
class BankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankService service;

    @MockBean
    private BankRepository bankRepository;

    @MockBean
    private BankValidator bankValidator;

    @Test
    void testGetBanks() throws Exception {
        Bank bank1 = new Bank(1, "Bank 1", "123456789");
        Bank bank2 = new Bank(2, "Bank 2", "987654321");
        List<Bank> forms = Arrays.asList(bank1, bank2);
        when(service.findAll()).thenReturn(forms);


        mockMvc.perform(MockMvcRequestBuilders.get("/bank")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Bank 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bik").value("123456789"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Bank 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].bik").value("987654321"));
    }

    @Test
    void testGetBanksById() throws Exception{
        Bank bank = new Bank(1, "Bank 1", "123456789");

        when(service.findOne(1)).thenReturn(bank);


        mockMvc.perform(MockMvcRequestBuilders.get("/bank/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Bank 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bik").value("123456789"));
    }



}
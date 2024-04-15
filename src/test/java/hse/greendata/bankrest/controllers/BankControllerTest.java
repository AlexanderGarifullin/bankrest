package hse.greendata.bankrest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import hse.greendata.bankrest.dto.BankDTO;
import hse.greendata.bankrest.models.Bank;
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
import static org.mockito.Mockito.verify;
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

    @Test
    void testCreateBank() throws Exception {
        BankDTO bankDTO = new BankDTO();

        bankDTO.setName("Test Bank");
        bankDTO.setBik("123456789");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(bankDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/bank")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateBankWithEmptyName() throws Exception {
        BankDTO bankDTO = new BankDTO();

        bankDTO.setName("");
        bankDTO.setBik("123456789");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(bankDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/bank")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateBank() throws Exception {
        int id = 1;
        BankDTO bankDTO = new BankDTO();
        bankDTO.setName("Bank");
        bankDTO.setBik("123456789");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(bankDTO);

        mockMvc.perform(MockMvcRequestBuilders.patch("/bank/{id}", id)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateBankWithEmptyName() throws Exception {
        int id = 1;
        BankDTO bankDTO = new BankDTO();
        bankDTO.setName("");
        bankDTO.setBik("123456789");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(bankDTO);

        mockMvc.perform(MockMvcRequestBuilders.patch("/bank/{id}", id)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteBank() throws Exception{
        int idToDelete = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/bank/{id}", idToDelete)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).delete(idToDelete);
    }

}
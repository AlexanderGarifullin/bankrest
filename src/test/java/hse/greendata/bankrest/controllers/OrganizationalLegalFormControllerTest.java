package hse.greendata.bankrest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import hse.greendata.bankrest.dto.OrganizationalLegalFormDTO;
import hse.greendata.bankrest.models.OrganizationalLegalForm;
import hse.greendata.bankrest.repositories.OrganizationalLegalFormRepository;
import hse.greendata.bankrest.services.OrganizationalLegalFormService;
import hse.greendata.bankrest.util.validators.OrganizationalLegalFormValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.Mockito.*;;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrganizationalLegalFormController.class)
class OrganizationalLegalFormControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrganizationalLegalFormService service;

    @MockBean
    private OrganizationalLegalFormRepository repository;

    @MockBean
    private OrganizationalLegalFormValidator validator;


    @Test
    void testGetOrganizationalLegalForms() throws Exception {
        OrganizationalLegalForm form1 = new OrganizationalLegalForm(1, "Form 1");
        OrganizationalLegalForm form2 = new OrganizationalLegalForm(2, "Form 2");
        List<OrganizationalLegalForm> forms = Arrays.asList(form1, form2);
        when(service.findAll("id")).thenReturn(forms);


        mockMvc.perform(MockMvcRequestBuilders.get("/olf")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Form 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Form 2"));
    }

    @Test
    void testGetOrganizationalLegalFormById() throws Exception{
        OrganizationalLegalForm form = new OrganizationalLegalForm(1, "Form 1");

        when(service.findOne(1)).thenReturn(form);

        String expectedJsonResponse = "{\"name\":\"Form 1\"}";

        mockMvc.perform(MockMvcRequestBuilders.get("/olf/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedJsonResponse));
    }

    @Test
    void testCreateOrganizationalLegalForm() throws Exception {
        OrganizationalLegalFormDTO formDTO = new OrganizationalLegalFormDTO();
        formDTO.setName("Test Form");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(formDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/olf")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateOrganizationalLegalFormWithEmptyName() throws Exception {
        OrganizationalLegalFormDTO formDTO = new OrganizationalLegalFormDTO();
        formDTO.setName("");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(formDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/olf")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateOrganizationalLegalForm() throws Exception {
        int id = 1;
        OrganizationalLegalFormDTO formDTO = new OrganizationalLegalFormDTO();
        formDTO.setName("Updated Form");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(formDTO);

        mockMvc.perform(MockMvcRequestBuilders.patch("/olf/{id}", id)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateOrganizationalLegalFormWithEmptyName() throws Exception {
        int id = 1;
        OrganizationalLegalFormDTO formDTO = new OrganizationalLegalFormDTO();
        formDTO.setName("");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(formDTO);

        mockMvc.perform(MockMvcRequestBuilders.patch("/olf/{id}", id)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDelete() throws Exception{
        int idToDelete = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/olf/{id}", idToDelete)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).delete(idToDelete);
      }
}
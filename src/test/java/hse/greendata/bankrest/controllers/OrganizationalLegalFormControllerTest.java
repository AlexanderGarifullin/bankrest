package hse.greendata.bankrest.controllers;

import hse.greendata.bankrest.models.OrganizationalLegalForm;
import hse.greendata.bankrest.services.OrganizationalLegalFormService;
import hse.greendata.bankrest.util.validators.OrganizationalLegalFormValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(OrganizationalLegalFormController.class)
class OrganizationalLegalFormControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrganizationalLegalFormService service;

    @MockBean
    private OrganizationalLegalFormValidator validator;


    @Test
    void getOrganizationalLegalForms() throws Exception {
        OrganizationalLegalForm form1 = new OrganizationalLegalForm(1, "Form 1");
        OrganizationalLegalForm form2 = new OrganizationalLegalForm(2, "Form 2");
        List<OrganizationalLegalForm> forms = Arrays.asList(form1, form2);
        when(service.findAll()).thenReturn(forms);


        mockMvc.perform(MockMvcRequestBuilders.get("/olf")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Form 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Form 2"));
    }

    @Test
    void testGetOrganizationalLegalForms() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}
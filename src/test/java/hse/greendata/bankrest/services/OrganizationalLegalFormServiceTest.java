package hse.greendata.bankrest.services;

import hse.greendata.bankrest.models.Bank;
import hse.greendata.bankrest.models.OrganizationalLegalForm;
import hse.greendata.bankrest.repositories.OrganizationalLegalFormRepository;
import hse.greendata.bankrest.util.exceptions.Bank.BankNotFoundException;
import hse.greendata.bankrest.util.exceptions.OrganizationalLegalForm.OrganizationalLegalFormNotFoundException;
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
class OrganizationalLegalFormServiceTest {

    @Mock
    private OrganizationalLegalFormRepository organizationalLegalFormRepository;

    @InjectMocks
    private OrganizationalLegalFormService organizationalLegalFormService;

    @Test
    void testFindOne_WhenFormExists_ReturnForm() {
        OrganizationalLegalForm form = new OrganizationalLegalForm();
        form.setId(1);
        form.setName("Test form");

        when(organizationalLegalFormRepository.findById(1)).thenReturn(Optional.of(form));


        OrganizationalLegalForm result = organizationalLegalFormService.findOne(1);

        assertEquals(form.getId(), result.getId());
        assertEquals(form.getName(), result.getName());

        verify(organizationalLegalFormRepository).findById(1);
    }

    @Test
    void testFindOne_WhenFormNotExists_ThrowException() {
        when(organizationalLegalFormRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(OrganizationalLegalFormNotFoundException.class, () -> {
            organizationalLegalFormService.findOne(1);
        });

        verify(organizationalLegalFormRepository).findById(1);
    }

}
package hse.greendata.bankrest.services;

import hse.greendata.bankrest.models.OrganizationalLegalForm;
import hse.greendata.bankrest.repositories.OrganizationalLegalFormRepository;
import hse.greendata.bankrest.util.exceptions.OrganizationalLegalForm.OrganizationalLegalFormIllegalSortArgument;
import hse.greendata.bankrest.util.exceptions.OrganizationalLegalForm.OrganizationalLegalFormNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Test
    void testFindAll_WhenFormsExists_ReturnListOfForms() {
        OrganizationalLegalForm form = new OrganizationalLegalForm(1, "form");
        List<OrganizationalLegalForm> expectedForms = List.of(form);

        when(organizationalLegalFormRepository.findAll(Sort.by("id"))).thenReturn(expectedForms);

        List<OrganizationalLegalForm> actualForms = organizationalLegalFormService.findAll("id");

        assertEquals(expectedForms.size(), actualForms.size());
        for (int i = 0; i < expectedForms.size(); i++) {
            OrganizationalLegalForm form1 = expectedForms.get(i);
            OrganizationalLegalForm form2 = expectedForms.get(i);
            assertEquals(form1.getId(), form2.getId());
            assertEquals(form1.getName(), form2.getName());
        }

        verify(organizationalLegalFormRepository, times(1)).findAll(Sort.by("id"));
    }


    @Test
    void testFindAll_WithIllegalParameter_ReturnIllegalSortArgumentExectpion() {
        assertThrows(OrganizationalLegalFormIllegalSortArgument.class, () -> {
            organizationalLegalFormService.findAll("invalidField");
        });
    }

    @Test
    void testFindOneByName_WhenFormExist_ReturnForm() {
        OrganizationalLegalForm form = new OrganizationalLegalForm(1, "form");
        when(organizationalLegalFormRepository.findByName(form.getName())).thenReturn(Optional.of(form));

        Optional<OrganizationalLegalForm> result = organizationalLegalFormService.findOne(form.getName());

        assert(result.isPresent());
        OrganizationalLegalForm f = result.orElse(null);
        assertEquals(form.getId(),f.getId());
        assertEquals(form.getName(), f.getName());

        verify(organizationalLegalFormRepository, times(1)).findByName(form.getName());
    }

    @Test
    void testFindOneById_WhenFormExist_ReturnForm() {
        OrganizationalLegalForm form = new OrganizationalLegalForm(1, "form");
        when(organizationalLegalFormRepository.findById(form.getId())).thenReturn(Optional.of(form));

        Optional<OrganizationalLegalForm> result = organizationalLegalFormService.findById(form.getId());

        assert(result.isPresent());
        OrganizationalLegalForm b = result.orElse(null);
        assertEquals(form.getId(),b.getId());
        assertEquals(form.getName(), b.getName());

        verify(organizationalLegalFormRepository, times(1)).findById(form.getId());
    }

    @Test
    void testSave() {
        OrganizationalLegalForm form = new OrganizationalLegalForm(1, "form");

        organizationalLegalFormService.save(form);

        verify(organizationalLegalFormRepository, times(1))
                .save(any(OrganizationalLegalForm.class));
    }

    @Test
    void testUpdate() {
        int id = 1;
        OrganizationalLegalForm form = new OrganizationalLegalForm(2, "form");

        organizationalLegalFormService.update(id, form);

        verify(organizationalLegalFormRepository, times(1)).save(form);

        assertEquals(id, form.getId());
    }

    @Test
    void testDelete() {
        int id = 1;

        organizationalLegalFormService.delete(id);

        verify(organizationalLegalFormRepository, times(1)).deleteById(id);
    }

}
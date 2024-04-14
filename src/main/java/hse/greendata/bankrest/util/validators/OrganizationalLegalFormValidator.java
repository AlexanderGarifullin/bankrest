package hse.greendata.bankrest.util.validators;

import hse.greendata.bankrest.models.OrganizationalLegalForm;
import hse.greendata.bankrest.services.OrganizationalLegalFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class OrganizationalLegalFormValidator implements Validator {

    private final OrganizationalLegalFormService organizationalLegalFormService;

    @Autowired
    public OrganizationalLegalFormValidator(OrganizationalLegalFormService organizationalLegalFormService) {
        this.organizationalLegalFormService = organizationalLegalFormService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return OrganizationalLegalFormValidator.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OrganizationalLegalForm organizationalLegalForm = (OrganizationalLegalForm) target;

        if (organizationalLegalFormService.findOne(organizationalLegalForm.getName()).isPresent()) {
            errors.rejectValue("name", "", "this name is already taken");
        }
    }
}

package hse.greendata.bankrest.util.validators;

import hse.greendata.bankrest.models.Client;
import hse.greendata.bankrest.services.ClientService;
import hse.greendata.bankrest.services.OrganizationalLegalFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ClientValidator implements Validator {
    private final ClientService clientService;
    private final OrganizationalLegalFormService organizationalLegalFormService;

    @Autowired
    public ClientValidator(ClientService clientService, OrganizationalLegalFormService organizationalLegalFormService) {
        this.clientService = clientService;
        this.organizationalLegalFormService = organizationalLegalFormService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Client.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Client client = (Client) target;

        if (organizationalLegalFormService.findById(client.getOrganizationalLegalFormId()).isEmpty()) {
            errors.rejectValue("organizationalLegalFormId", "",
                    "there is no such OrganizationalLegalForm");
        }

        if (clientService.findOneByName(client.getName()).isPresent()) {
            errors.rejectValue("name", "", "this name is already taken");
        }

        if (clientService.findOneByShortName(client.getShortName()).isPresent()) {
            errors.rejectValue("shortName", "", "this shortName is already taken");
        }
    }
}

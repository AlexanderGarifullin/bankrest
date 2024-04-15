package hse.greendata.bankrest.util.validators;

import hse.greendata.bankrest.models.Deposit;
import hse.greendata.bankrest.services.BankService;
import hse.greendata.bankrest.services.ClientService;
import hse.greendata.bankrest.services.DepositService;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

@Component
public class DepositValidator implements Validator {

    private final DepositService depositService;
    private final ClientService clientService;
    private final BankService bankService;

    @Autowired
    public DepositValidator(DepositService depositService, ClientService clientService, BankService bankService) {
        this.depositService = depositService;
        this.clientService = clientService;
        this.bankService = bankService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Deposit.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Deposit deposit = (Deposit) target;

        if (clientService.findOneById(deposit.getClientId()).isEmpty()) {
            errors.rejectValue("clientId", "",
                    "there is no such Client");
        }

        if (bankService.findOneById(deposit.getBankId()).isEmpty()) {
            errors.rejectValue("bankId", "",
                    "there is no such Bank");
        }

        Date comparisonDate = new Date(2000, 1, 1);
        if (comparisonDate.compareTo(deposit.getOpeningDate()) >= 0) {
            errors.rejectValue("openingDate", "",
                    "openingDate should be after 2000-01-01");
        }

    }
}

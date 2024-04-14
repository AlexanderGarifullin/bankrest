package hse.greendata.bankrest.util.validators;

import hse.greendata.bankrest.models.Bank;
import hse.greendata.bankrest.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BankValidator implements Validator {

    private final BankService bankService;

    @Autowired
    public BankValidator(BankService bankService) {
        this.bankService = bankService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Bank.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Bank bank = (Bank) target;

        if (bankService.findOneByName(bank.getName()).isPresent()) {
            errors.rejectValue("name", "", "this name is already taken");
        }

        if (bankService.findOneByBik(bank.getBik()).isPresent()) {
            errors.rejectValue("bik", "", "this bik is already taken");
        }
    }
}

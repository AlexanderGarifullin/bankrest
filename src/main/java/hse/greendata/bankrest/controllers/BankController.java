package hse.greendata.bankrest.controllers;

import hse.greendata.bankrest.dto.BankDTO;
import hse.greendata.bankrest.dto.OrganizationalLegalFormDTO;
import hse.greendata.bankrest.models.Bank;
import hse.greendata.bankrest.models.OrganizationalLegalForm;
import hse.greendata.bankrest.services.BankService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank")
public class BankController {
    private final BankService bankService;
    private final ModelMapper modelMapper;
    @Autowired
    public BankController(BankService bankService, ModelMapper modelMapper) {
        this.bankService = bankService;
        this.modelMapper = modelMapper;
    }

    private Bank convertToBank(BankDTO bankDTO) {
        return modelMapper.map(bankDTO, Bank.class);
    }

    private BankDTO convertToBankDTO(Bank bank){
        return modelMapper.map(bank, BankDTO.class);
    }
}

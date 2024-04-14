package hse.greendata.bankrest.controllers;

import hse.greendata.bankrest.dto.BankDTO;
import hse.greendata.bankrest.dto.OrganizationalLegalFormDTO;
import hse.greendata.bankrest.models.Bank;
import hse.greendata.bankrest.models.OrganizationalLegalForm;
import hse.greendata.bankrest.services.BankService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("")
    public List<BankDTO> getBanks(){
        return bankService.findAll().stream().map(this::convertToBankDTO)
                .collect(Collectors.toList());
    }

    private Bank convertToBank(BankDTO bankDTO) {
        return modelMapper.map(bankDTO, Bank.class);
    }

    private BankDTO convertToBankDTO(Bank bank){
        return modelMapper.map(bank, BankDTO.class);
    }
}

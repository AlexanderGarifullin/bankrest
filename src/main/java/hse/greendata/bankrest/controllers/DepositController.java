package hse.greendata.bankrest.controllers;

import hse.greendata.bankrest.dto.ClientDTO;
import hse.greendata.bankrest.dto.DepositDTO;
import hse.greendata.bankrest.models.Client;
import hse.greendata.bankrest.models.Deposit;
import hse.greendata.bankrest.services.DepositService;
import hse.greendata.bankrest.util.exceptions.ErrorMessagesBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/deposit")
public class DepositController {
    private final DepositService depositService;
    private final ModelMapper modelMapper;
    private final ErrorMessagesBuilder errorMessagesBuilder;
    @Autowired
    public DepositController(DepositService depositService, ModelMapper modelMapper, ErrorMessagesBuilder errorMessagesBuilder) {
        this.depositService = depositService;
        this.modelMapper = modelMapper;
        this.errorMessagesBuilder = errorMessagesBuilder;
    }

    @GetMapping("")
    public List<Deposit> getClients(){
        return depositService.findAll();
    }

    @GetMapping("/{id}")
    public DepositDTO getClients(@PathVariable("id") int id){
        return convertToDepositDTO(depositService.findOne(id));
    }

    private DepositDTO convertToDepositDTO(Deposit deposit){
        return modelMapper.map(deposit, DepositDTO.class);
    }
}

package hse.greendata.bankrest.controllers;

import hse.greendata.bankrest.dto.BankDTO;
import hse.greendata.bankrest.dto.OrganizationalLegalFormDTO;
import hse.greendata.bankrest.models.Bank;
import hse.greendata.bankrest.models.OrganizationalLegalForm;
import hse.greendata.bankrest.services.BankService;
import hse.greendata.bankrest.util.exceptions.Bank.BankException;
import hse.greendata.bankrest.util.exceptions.ErrorResponse;
import hse.greendata.bankrest.util.exceptions.OrganizationalLegalForm.OrganizationalLegalFormException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public BankDTO getBanks(@PathVariable("id") int id){
        return convertToBankDTO(bankService.findOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        bankService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler(BankException.class)
    public ResponseEntity<ErrorResponse> handleBankException(BankException ex) {
        ErrorResponse response = new ErrorResponse(
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Bank convertToBank(BankDTO bankDTO) {
        return modelMapper.map(bankDTO, Bank.class);
    }

    private BankDTO convertToBankDTO(Bank bank){
        return modelMapper.map(bank, BankDTO.class);
    }
}

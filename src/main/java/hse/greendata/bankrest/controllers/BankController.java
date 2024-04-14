package hse.greendata.bankrest.controllers;

import hse.greendata.bankrest.dto.BankDTO;
import hse.greendata.bankrest.dto.OrganizationalLegalFormDTO;
import hse.greendata.bankrest.models.Bank;
import hse.greendata.bankrest.services.BankService;
import hse.greendata.bankrest.util.exceptions.Bank.BankException;
import hse.greendata.bankrest.util.exceptions.Bank.BankNotCreatedException;
import hse.greendata.bankrest.util.exceptions.ErrorMessagesBuilder;
import hse.greendata.bankrest.util.exceptions.ErrorResponse;
import hse.greendata.bankrest.util.exceptions.OrganizationalLegalForm.OrganizationalLegalFormException;
import hse.greendata.bankrest.util.exceptions.OrganizationalLegalForm.OrganizationalLegalFormNotCreatedException;
import hse.greendata.bankrest.util.validators.BankValidator;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bank")
public class BankController {
    private final BankService bankService;
    private final ModelMapper modelMapper;
    private final ErrorMessagesBuilder errorMessagesBuilder;
    private final BankValidator bankValidator;
    @Autowired
    public BankController(BankService bankService, ModelMapper modelMapper, ErrorMessagesBuilder errorMessagesBuilder, BankValidator bankValidator) {
        this.bankService = bankService;
        this.modelMapper = modelMapper;
        this.errorMessagesBuilder = errorMessagesBuilder;
        this.bankValidator = bankValidator;
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

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid BankDTO bankDTO,
                                             BindingResult bindingResult){
        bankValidator.validate(convertToBank(bankDTO),
                bindingResult);

        if (bindingResult.hasErrors()){
            throwException(bindingResult, BankNotCreatedException.class);
        }
        bankService.save(convertToBank(bankDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @SneakyThrows
    private void throwException(BindingResult bindingResult, Class<? extends BankException> exceptionClass){
        throw exceptionClass.getDeclaredConstructor(String.class)
                .newInstance(errorMessagesBuilder.buildErrorMessages(bindingResult));
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

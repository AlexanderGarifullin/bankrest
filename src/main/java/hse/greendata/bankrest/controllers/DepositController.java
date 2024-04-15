package hse.greendata.bankrest.controllers;

import hse.greendata.bankrest.dto.DepositDTO;
import hse.greendata.bankrest.models.Deposit;
import hse.greendata.bankrest.services.DepositService;
import hse.greendata.bankrest.util.exceptions.Deposit.DepositException;
import hse.greendata.bankrest.util.exceptions.Deposit.DepositNotCreatedException;
import hse.greendata.bankrest.util.exceptions.Deposit.DepositNotUpdatedException;
import hse.greendata.bankrest.util.exceptions.ErrorMessagesBuilder;
import hse.greendata.bankrest.util.exceptions.ErrorResponse;
import hse.greendata.bankrest.util.validators.DepositValidator;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deposit")
public class DepositController {
    private final DepositService depositService;
    private final ModelMapper modelMapper;
    private final ErrorMessagesBuilder errorMessagesBuilder;
    private final DepositValidator depositValidator;
    @Autowired
    public DepositController(DepositService depositService, ModelMapper modelMapper, ErrorMessagesBuilder errorMessagesBuilder, DepositValidator depositValidator) {
        this.depositService = depositService;
        this.modelMapper = modelMapper;
        this.errorMessagesBuilder = errorMessagesBuilder;
        this.depositValidator = depositValidator;
    }

    @GetMapping("")
    public List<Deposit> getClients(@RequestParam(required = false) String sort){
        if (sort == null) {
            sort = "id";
        }
        return depositService.findAll(sort);
    }

    @GetMapping("/{id}")
    public DepositDTO getClients(@PathVariable("id") int id){
        return convertToDepositDTO(depositService.findOne(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid DepositDTO depositDTO,
                                             BindingResult bindingResult){
        depositValidator.validate(convertToDeposit(depositDTO),
                bindingResult);

        if (bindingResult.hasErrors()){
            throwException(bindingResult, DepositNotCreatedException.class);
        }

        depositService.save(convertToDeposit(depositDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid DepositDTO depositDTO,
                                             BindingResult bindingResult,
                                             @PathVariable("id") int id) {
        depositValidator.validate(convertToDeposit(depositDTO),
                bindingResult);

        if (bindingResult.hasErrors()){
            throwException(bindingResult, DepositNotUpdatedException.class);
        }


        depositService.update(id, convertToDeposit(depositDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        depositService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @SneakyThrows
    private void throwException(BindingResult bindingResult, Class<? extends DepositException> exceptionClass){
        throw exceptionClass.getDeclaredConstructor(String.class)
                .newInstance(errorMessagesBuilder.buildErrorMessages(bindingResult));
    }

    @ExceptionHandler(DepositException.class)
    public ResponseEntity<ErrorResponse> handleDepositException(DepositException ex) {
        ErrorResponse response = new ErrorResponse(
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Deposit convertToDeposit(DepositDTO depositDTO) {
        Deposit deposit = new Deposit();
        deposit.setOpeningDate(depositDTO.getOpeningDate());
        deposit.setInterestRate(depositDTO.getInterestRate());
        deposit.setTermMonths(depositDTO.getTermMonths());
        deposit.setBankId(depositDTO.getBankId());
        deposit.setClientId(depositDTO.getClientId());
        return deposit;
    }

    private DepositDTO convertToDepositDTO(Deposit deposit){
        return modelMapper.map(deposit, DepositDTO.class);
    }
}

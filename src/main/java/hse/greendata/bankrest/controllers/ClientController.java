package hse.greendata.bankrest.controllers;

import hse.greendata.bankrest.dto.ClientDTO;
import hse.greendata.bankrest.models.Client;
import hse.greendata.bankrest.services.ClientService;
import hse.greendata.bankrest.services.OrganizationalLegalFormService;
import hse.greendata.bankrest.util.exceptions.Client.ClientException;
import hse.greendata.bankrest.util.exceptions.Client.ClientNotCreatedException;
import hse.greendata.bankrest.util.exceptions.Client.ClientNotUpdatedException;
import hse.greendata.bankrest.util.exceptions.ErrorMessagesBuilder;
import hse.greendata.bankrest.util.exceptions.ErrorResponse;
import hse.greendata.bankrest.util.validators.ClientValidator;
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
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;
    private final ModelMapper modelMapper;
    private final ErrorMessagesBuilder errorMessagesBuilder;

    private final ClientValidator clientValidator;

    @Autowired
    public ClientController(ClientService clientService, OrganizationalLegalFormService organizationalLegalFormService, ModelMapper modelMapper, ErrorMessagesBuilder errorMessagesBuilder, ClientValidator clientValidator) {
        this.clientService = clientService;
        this.clientValidator = clientValidator;
        this.modelMapper = modelMapper;
        this.errorMessagesBuilder = errorMessagesBuilder;
    }

    @GetMapping("")
    public List<Client> getClients(@RequestParam(required = false) String sort){
        if (sort == null) {
            sort = "id";
        }
        return clientService.findAll(sort);
    }

    @GetMapping("/{id}")
    public ClientDTO getClients(@PathVariable("id") int id){
        return convertToClientDTO(clientService.findOne(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid ClientDTO clientDTO,
                                             BindingResult bindingResult){
        clientValidator.validate(convertToClient(clientDTO),
                bindingResult);

        if (bindingResult.hasErrors()){
            throwException(bindingResult, ClientNotCreatedException.class);
        }
        clientService.save(convertToClient(clientDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid ClientDTO clientDTO,
                                             BindingResult bindingResult,
                                             @PathVariable("id") int id) {
        clientValidator.validate(convertToClient(clientDTO),
                bindingResult);

        if (bindingResult.hasErrors()){
            throwException(bindingResult, ClientNotUpdatedException.class);
        }
        clientService.update(id, convertToClient(clientDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        clientService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @SneakyThrows
    private void throwException(BindingResult bindingResult, Class<? extends ClientException> exceptionClass){
        throw exceptionClass.getDeclaredConstructor(String.class)
                .newInstance(errorMessagesBuilder.buildErrorMessages(bindingResult));
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ErrorResponse> handleClientException(ClientException ex) {
        ErrorResponse response = new ErrorResponse(
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    private Client convertToClient(ClientDTO clientDTO) {
        return modelMapper.map(clientDTO, Client.class);
    }

    private ClientDTO convertToClientDTO(Client client){
        return modelMapper.map(client, ClientDTO.class);
    }
}

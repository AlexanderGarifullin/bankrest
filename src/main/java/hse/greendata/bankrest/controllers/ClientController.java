package hse.greendata.bankrest.controllers;

import hse.greendata.bankrest.dto.BankDTO;
import hse.greendata.bankrest.dto.ClientDTO;
import hse.greendata.bankrest.dto.OrganizationalLegalFormDTO;
import hse.greendata.bankrest.models.Bank;
import hse.greendata.bankrest.models.Client;
import hse.greendata.bankrest.models.OrganizationalLegalForm;
import hse.greendata.bankrest.services.ClientService;
import hse.greendata.bankrest.util.exceptions.Bank.BankException;
import hse.greendata.bankrest.util.exceptions.Bank.BankNotCreatedException;
import hse.greendata.bankrest.util.exceptions.Client.ClientException;
import hse.greendata.bankrest.util.exceptions.ErrorMessagesBuilder;
import hse.greendata.bankrest.util.exceptions.ErrorResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;
    private final ModelMapper modelMapper;
    private final ErrorMessagesBuilder errorMessagesBuilder;

    @Autowired
    public ClientController(ClientService clientService, ModelMapper modelMapper, ErrorMessagesBuilder errorMessagesBuilder) {
        this.clientService = clientService;
        this.modelMapper = modelMapper;
        this.errorMessagesBuilder = errorMessagesBuilder;
    }

    @GetMapping("")
    public List<Client> getClients(){
        return clientService.findAll();
    }

    @GetMapping("/{id}")
    public ClientDTO getClients(@PathVariable("id") int id){
        return convertToClientDTO(clientService.findOne(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid ClientDTO clientDTO,
                                             BindingResult bindingResult){
//        bankValidator.validate(convertToBank(bankDTO),
//                bindingResult);

//        if (bindingResult.hasErrors()){
//            throwException(bindingResult, BankNotCreatedException.class);
//        }
        clientService.save(convertToClient(clientDTO));
        return ResponseEntity.ok(HttpStatus.OK);
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
        Client client = modelMapper.map(clientDTO, Client.class);

        // Преобразование OrganizationalLegalFormDTO в OrganizationalLegalForm
        OrganizationalLegalForm organizationalLegalForm =
                modelMapper.map(clientDTO.getOrganizationalLegalForm(), OrganizationalLegalForm.class);

        // Установка преобразованного OrganizationalLegalForm в Client
        client.setOrganizationalLegalForm(organizationalLegalForm);
        return client;
    }


    private ClientDTO convertToClientDTO(Client client){
        ClientDTO clientDTO = modelMapper.map(client, ClientDTO.class);
        OrganizationalLegalFormDTO organizationalLegalFormDTO =
                modelMapper.map(client.getOrganizationalLegalForm(), OrganizationalLegalFormDTO.class);
        clientDTO.setOrganizationalLegalForm(organizationalLegalFormDTO);
        return clientDTO;
    }
}

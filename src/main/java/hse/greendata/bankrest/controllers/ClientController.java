package hse.greendata.bankrest.controllers;

import hse.greendata.bankrest.dto.BankDTO;
import hse.greendata.bankrest.dto.ClientDTO;
import hse.greendata.bankrest.dto.OrganizationalLegalFormDTO;
import hse.greendata.bankrest.models.Bank;
import hse.greendata.bankrest.models.Client;
import hse.greendata.bankrest.services.ClientService;
import hse.greendata.bankrest.util.exceptions.Bank.BankException;
import hse.greendata.bankrest.util.exceptions.Client.ClientException;
import hse.greendata.bankrest.util.exceptions.ErrorMessagesBuilder;
import hse.greendata.bankrest.util.exceptions.ErrorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<ClientDTO> getClients(){
        return clientService.findAll().stream().map(this::convertToClientDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ClientDTO getClients(@PathVariable("id") int id){
        return convertToClientDTO(clientService.findOne(id));
    }



    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ErrorResponse> handleClientException(ClientException ex) {
        ErrorResponse response = new ErrorResponse(
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private ClientDTO convertToClientDTO(Client client){
        ClientDTO clientDTO = modelMapper.map(client, ClientDTO.class);
        OrganizationalLegalFormDTO organizationalLegalFormDTO =
                modelMapper.map(client.getOrganizationalLegalForm(), OrganizationalLegalFormDTO.class);
        clientDTO.setOrganizationalLegalForm(organizationalLegalFormDTO);
        return clientDTO;
    }
}

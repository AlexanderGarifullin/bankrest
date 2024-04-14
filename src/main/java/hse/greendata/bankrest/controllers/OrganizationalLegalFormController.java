package hse.greendata.bankrest.controllers;

import hse.greendata.bankrest.dto.OrganizationalLegalFormDTO;
import hse.greendata.bankrest.models.OrganizationalLegalForm;
import hse.greendata.bankrest.services.OrganizationalLegalFormService;
import hse.greendata.bankrest.util.OrganizationalLegalForm.ErrorResponse;
import hse.greendata.bankrest.util.OrganizationalLegalForm.exception.OrganizationalLegalForm.OrganizationalLegalFormException;
import hse.greendata.bankrest.util.OrganizationalLegalForm.exception.OrganizationalLegalForm.OrganizationalLegalFormNotCreatedException;
import hse.greendata.bankrest.util.OrganizationalLegalForm.exception.OrganizationalLegalForm.OrganizationalLegalFormNotUpdatedException;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/olf")
public class OrganizationalLegalFormController {

    private final OrganizationalLegalFormService organizationalLegalFormService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrganizationalLegalFormController(OrganizationalLegalFormService organizationalLegalFormService, ModelMapper modelMapper) {
        this.organizationalLegalFormService = organizationalLegalFormService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public List<OrganizationalLegalFormDTO> getOrganizationalLegalForms(){
        return organizationalLegalFormService.findAll().stream().map(this::convertToOrganizationalLegalFormDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrganizationalLegalFormDTO getOrganizationalLegalForms(@PathVariable("id") int id){
        return convertToOrganizationalLegalFormDTO(organizationalLegalFormService.findOne(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid OrganizationalLegalFormDTO organizationalLegalFormDTO,
                                              BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throwException(bindingResult, OrganizationalLegalFormNotCreatedException.class);
        }
        organizationalLegalFormService.save(convertToOrganizationalLegalForm(organizationalLegalFormDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid OrganizationalLegalFormDTO organizationalLegalFormDTO,
                                             BindingResult bindingResult,
                                             @PathVariable("id") int id) {
        if (bindingResult.hasErrors()){
            throwException(bindingResult, OrganizationalLegalFormNotUpdatedException.class);
        }
        organizationalLegalFormService.update(id, convertToOrganizationalLegalForm(organizationalLegalFormDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        organizationalLegalFormService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @SneakyThrows
    private void throwException(BindingResult bindingResult, Class<? extends OrganizationalLegalFormException> exceptionClass){
        StringBuilder errorMsg = new StringBuilder();

        List<FieldError> errors = bindingResult.getFieldErrors();

        for (FieldError error : errors) {
            errorMsg.append(error.getField())
                    .append(" - ").append(error.getDefaultMessage())
                    .append(";");
        }
        OrganizationalLegalFormException exceptionInstance = exceptionClass.getDeclaredConstructor(String.class).newInstance(errorMsg.toString());
        throw exceptionInstance;
    }

    @ExceptionHandler(OrganizationalLegalFormException.class)
    public ResponseEntity<ErrorResponse> handleOrganizationalLegalFormException(OrganizationalLegalFormException ex) {
        ErrorResponse response = new ErrorResponse(
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private OrganizationalLegalForm convertToOrganizationalLegalForm(OrganizationalLegalFormDTO organizationalLegalFormDTO) {
        return modelMapper.map(organizationalLegalFormDTO, OrganizationalLegalForm.class);
    }

    private OrganizationalLegalFormDTO convertToOrganizationalLegalFormDTO(OrganizationalLegalForm OrganizationalLegalForm){
        return modelMapper.map(OrganizationalLegalForm, OrganizationalLegalFormDTO.class);
    }
}

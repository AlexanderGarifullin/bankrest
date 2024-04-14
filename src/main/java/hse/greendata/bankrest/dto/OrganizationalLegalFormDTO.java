package hse.greendata.bankrest.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationalLegalFormDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 100, message = "Name should be between 1 and 100 characters")
    private String name;
}

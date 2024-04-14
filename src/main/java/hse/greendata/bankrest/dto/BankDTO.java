package hse.greendata.bankrest.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 255, message = "Name should be between 1 and 255 characters")
    private String name;

    @NotBlank(message = "BIK is required")
    @Pattern(regexp = "^\\d{9}$",
            message = "BIK must be a 9-digit number")
    private String bik;
}

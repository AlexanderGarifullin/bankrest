package hse.greendata.bankrest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDTO {
    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 255, message = "Name should be between 1 and 255 characters")
    private String name;

    @NotBlank(message = "Short name is required")
    @Size(min = 1, max = 50, message = "Name should be between 1 and 50 characters")
    private String shortName;

    @NotBlank(message = "Address is required")
    @Size(min = 1, max = 255, message = "Name should be between 1 and 255 characters")
    @Pattern(regexp = "^(?:[А-ЯЁ][а-яё]+),\\s(?:[А-ЯЁ][а-яё]+),\\s\\d{6},\\s(?:ул\\.|просп\\.)\\s(?:[А-ЯЁ][а-яё]+(?:\\s[А-ЯЁа-яё][а-яё]+)*)?,\\sд\\.\\s(?:[1-9]\\d*|0?[1-9]\\d*)$",
            message = "Address should be in this format: County, City, Index, Street, Home")
    private String address;

    @NotNull(message = "Organizational legal form id is required")
    private Integer organizationalLegalFormId;
}

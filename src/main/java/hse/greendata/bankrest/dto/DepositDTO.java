package hse.greendata.bankrest.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class DepositDTO {
    @NotNull(message = "Opening date is required")
    // !!! Should make validator for minDate !!!
    private Date openingDate;

    @NotNull(message = "Interest rate is required")
    @Min(value = 1, message = "Interest rate should be greater than zero")
    private Double interestRate;

    @NotNull(message = "Term months is required")
    @Min(value = 1, message = "Term months should be greater than zero")
    private Integer termMonths;

    @NotNull(message = "Bank id is required")
    private Integer bankId;

    @NotNull(message = "Client id is required")
    private Integer clientId;
}

package hse.greendata.bankrest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class DepositDTO {
    @NotNull(message = "Client id is required")
    private Integer clientId;

    @NotNull(message = "Bank id is required")
    private Integer bankId;

    @NotNull(message = "Opening date is required")
    private LocalDate openingDate;

    @NotNull(message = "Interest rate is required")
    @Min(value = 1, message = "Interest rate should be greater than zero")
    private Double interestRate;

    @NotNull(message = "Term months is required")
    @Min(value = 1, message = "Term months should be greater than zero")
    private Integer termMonths;
}

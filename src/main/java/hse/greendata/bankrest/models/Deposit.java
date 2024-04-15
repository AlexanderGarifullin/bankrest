package hse.greendata.bankrest.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "Deposit")
@Setter
@Getter
@NoArgsConstructor
public class Deposit {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "opening_date")
    @NotNull(message = "Opening date is required")
    // !!! Should make validator for minDate !!!
    private Date openingDate;

    @Column(name = "interest_rate")
    @NotNull(message = "Interest rate is required")
    @Min(value = 1, message = "Interest rate should be greater than zero")
    private Double interestRate;

    @Column(name = "term_months")
    @NotNull(message = "Term months is required")
    @Min(value = 1, message = "Term months should be greater than zero")
    private Integer termMonths;

    @Column(name = "bank_id")
    @NotNull(message = "Bank id is required")
    private Integer bankId;

    @Column(name = "client_id")
    @NotNull(message = "Client id is required")
    private Integer clientId;
}

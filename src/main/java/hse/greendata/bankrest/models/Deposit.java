package hse.greendata.bankrest.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Opening date is required")
    // !!! Should make validator for minDate !!!
    private Date openingDate;

    @Column(name = "interest_rate")
    @NotBlank(message = "Interest rate is required")
    @Min(value = 1, message = "Interest rate should be greater than zero")
    private Double interestRate;

    @Column(name = "term_months")
    @NotBlank(message = "Term months is required")
    @Min(value = 1, message = "Term months should be greater than zero")
    private Integer termMonths;

    @ManyToOne
    @NotBlank(message = "Bank is required")
    @JoinColumn(name = "bank_id", referencedColumnName = "id")
    private Bank bank;

    @ManyToOne
    @NotBlank(message = "Client is required")
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;
}

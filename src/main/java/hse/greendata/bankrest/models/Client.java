package hse.greendata.bankrest.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Client")
@Setter
@Getter
@NoArgsConstructor
public class Client {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 255, message = "Name should be between 1 and 255 characters")
    private String name;

    @Column(name = "short_name")
    @NotBlank(message = "Short name is required")
    @Size(min = 1, max = 50, message = "Name should be between 1 and 50 characters")
    private String shortName;

    @Column(name = "address")
    @NotBlank(message = "Address is required")
    @Size(min = 1, max = 255, message = "Name should be between 1 and 255 characters")
    @Pattern(regexp = "^(?:[А-ЯЁ][а-яё]+),\\s(?:[А-ЯЁ][а-яё]+),\\s\\d{6},\\s(?:ул\\.|просп\\.)\\s(?:[А-ЯЁ][а-яё]+(?:\\s[А-ЯЁа-яё][а-яё]+)*)?,\\sд\\.\\s(?:[1-9]\\d*|0?[1-9]\\d*)$",
            message = "Address should be in this format: County, City, Index, Street, Home")
    private String address;

    @ManyToOne
    @NotBlank(message = "Organizational legal form is required")
    @JoinColumn(name = "organizational_legal_form_id", referencedColumnName = "id")
    private OrganizationalLegalForm organizationalLegalForm;
}

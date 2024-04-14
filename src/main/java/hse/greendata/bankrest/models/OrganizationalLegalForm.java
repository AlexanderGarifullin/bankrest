package hse.greendata.bankrest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Organizational_legal_form")
@Setter
@Getter
@NoArgsConstructor
public class OrganizationalLegalForm {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 100, message = "Name should be between 1 and 100 characters")
    private String name;
}

package hse.greendata.bankrest.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Bank")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Bank {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 255, message = "Name should be between 1 and 255 characters")
    private String name;

    @Column(name = "bik")
    @NotBlank(message = "BIK is required")
    @Pattern(regexp = "^\\d{9}$",
            message = "BIK must be a 9-digit number")
    private String bik;
}

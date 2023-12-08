package kth.numi.userservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import kth.numi.userservice.roles.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private int id;

    @Schema(example = "John")
    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    @Schema(example = "Doe")
    private String lastname;

    @Column(name = "email", nullable = false, unique = true)
    @Schema(example = "john.doe@example.com")
    private String email;

    @Column(name = "password", nullable = false)
    @Schema(example = "password123")
    private String password;

    @Column(name = "phone")
    @Schema(example = "0764129242")
    private String phone;

    @Column(name = "address", columnDefinition = "TEXT")
    @Schema(example = "123 Main St, City, Country")
    private String address;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    @Schema(hidden = true)
    private Role role;
}
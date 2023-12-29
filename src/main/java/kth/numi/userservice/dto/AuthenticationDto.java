package kth.numi.userservice.dto;

import kth.numi.userservice.model.User;
import kth.numi.userservice.roles.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationDto {

    private Integer id;
    private Role role;
    private String access_token;

    public static AuthenticationDto convertToDto(User user, String access_token) {
        return new AuthenticationDto(
                user.getId(),
                user.getRole(),
                access_token
        );
    }
}
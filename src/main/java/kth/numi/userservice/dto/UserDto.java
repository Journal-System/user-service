package kth.numi.userservice.dto;

import kth.numi.userservice.model.User;
import kth.numi.userservice.roles.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String fullname;
    private String phone;
    private String email;
    private String address;
    private UserRole userRole;

    public static UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstname() + " " + user.getLastname(),
                user.getPhone(),
                user.getEmail(),
                user.getAddress(),
                user.getUserRole()
        );
    }

    public static <T extends User> List<UserDto> convertToDto(List<T> users) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (T user : users) {
            userDtoList.add(convertToDto(user));
        }
        return userDtoList;
    }
}
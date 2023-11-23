package kth.numi.userservice.service.User;

import kth.numi.userservice.model.User;
import kth.numi.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import static kth.numi.userservice.dto.UserDto.convertToDto;

@Service
public class UserServiceImpl implements UserService{

    final private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> getUser(Integer id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Could not find this user");
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(convertToDto(user.get()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("");
        }
    }

    @Override
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(convertToDto(users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No user found");
        }
    }
}

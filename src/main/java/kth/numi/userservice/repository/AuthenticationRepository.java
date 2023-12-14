package kth.numi.userservice.repository;

import kth.numi.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}

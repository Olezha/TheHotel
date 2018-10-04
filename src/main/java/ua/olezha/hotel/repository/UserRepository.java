package ua.olezha.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.olezha.hotel.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}

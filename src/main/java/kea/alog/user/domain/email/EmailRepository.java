package kea.alog.user.domain.email;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long>{
    Email findByEmail(String email);
}

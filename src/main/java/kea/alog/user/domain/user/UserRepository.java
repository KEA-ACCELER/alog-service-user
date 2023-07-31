package kea.alog.user.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUserPk(Long userPk);

    public User findByUserEmail(String userEmail);

    public boolean existsByUserNn(String userNn);


    
}

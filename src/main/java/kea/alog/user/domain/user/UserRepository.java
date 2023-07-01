package kea.alog.user.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUserPk(Long userPk);

    public boolean existsByUserId(String userId);

    public User findByUserId(String userId);

    public void deleteByUserId(String userId);
    
}

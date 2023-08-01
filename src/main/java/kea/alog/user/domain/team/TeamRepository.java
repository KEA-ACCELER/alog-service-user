package kea.alog.user.domain.team;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long>{

    Team findByTeamName(String teamName);

    boolean existsByTeamNameAndTeamLeaderPk(String teamName, Long teamLeaderPk);
    
}

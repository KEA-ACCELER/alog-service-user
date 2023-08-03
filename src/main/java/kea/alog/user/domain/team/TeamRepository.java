package kea.alog.user.domain.team;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface TeamRepository extends JpaRepository<Team, Long>{

    //중복 케이스가 많아서 아래 쿼리는 사용하지 않아야 함
    //Team findByTeamName(String teamName);

    boolean existsByTeamNameAndTeamLeaderPk(String teamName, Long teamLeaderPk);

    Team findByTeamNameAndTeamLeaderPk (String teamName, Long teamLeaderPk);

    Team findByTeamPk(Long teamPk);

    List<Team> findByTeamLeaderPk(Long teamLeaderPk);

    
}

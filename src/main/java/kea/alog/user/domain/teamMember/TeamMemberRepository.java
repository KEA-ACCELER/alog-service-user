package kea.alog.user.domain.teamMember;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kea.alog.user.domain.team.Team;
import kea.alog.user.domain.user.User;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long>{

    void deleteByTeamAndUser(Team team, User user);

    boolean existsByTeamAndUser(Team team, User user);

    List<TeamMember> findAllByTeam(Team team);
}

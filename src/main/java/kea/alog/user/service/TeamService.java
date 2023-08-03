package kea.alog.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kea.alog.user.domain.team.Team;
import kea.alog.user.domain.team.TeamRepository;
import kea.alog.user.domain.teamMember.TeamMember;
import kea.alog.user.domain.teamMember.TeamMemberRepository;
import kea.alog.user.web.dto.TeamDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {
    
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TeamMemberService teamMemberService;

    @Autowired
    TeamMemberRepository teamMemberRepository;

    @Transactional
    public String createTeam(TeamDto.CreateTeamRequestDto createTeamRequestDto, Long userPk) {
        Team team = Team.builder()
                                .teamName(createTeamRequestDto.getTeamName())
                                .teamLeaderPk(userPk)
                                .build();

        if (teamRepository.existsByTeamNameAndTeamLeaderPk(team.getTeamName(), team.getTeamLeaderPk())) {
            return "Team is already existed";
        }
        Team savedTeam = teamRepository.save(team);

        if (savedTeam == null) {
            return "making team is failed";
        }
        if (createTeamRequestDto.getUserNNList() == null){
            return "team created successfully without team members";
        }

        Boolean isallMemberSaved = teamMemberService.saveTeamMember(savedTeam, createTeamRequestDto.getUserNNList());

        if (!isallMemberSaved) {
            return "team member save failed";
        }

        return "team created successfully";

    }

    @Transactional
    public String deleteTeam(Long teamPk, Long userPk) {

        Team team = teamRepository.findByTeamPk(teamPk);
        if (team == null) {
            return "team is not existed";
        }

        if(team.getTeamLeaderPk() != userPk){
            return "you are not team leader";
        }

        teamRepository.delete(team);
        return "team deleted successfully";
    }

    @Transactional
    public Team getTeamInfo(Long teamPk, Long userPk) {
        Team team = teamRepository.findByTeamPk(teamPk);
        
        if (team == null) {
            log.info("team is not existed, so that team info is null");
            return null;
        }
        if (team.getTeamLeaderPk() == userPk) {
            return team;
        }
        List<TeamMember> teamMemberList = teamMemberRepository.findAllByTeam(team);
        for (TeamMember teamMember : teamMemberList) {
            if (teamMember.getUser().getUserPk() == userPk) {
                return team;
            }
        }
        log.info("you are not authorized to access this team info");
        return null;
    }

    public List<TeamMember> getJoinedTeamList(Long userPk) {
        return teamMemberRepository.findAllByUserUserPk(userPk);
    }
        
    

}

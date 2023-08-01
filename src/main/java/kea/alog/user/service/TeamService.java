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
            return "이미 존재하는 팀입니다";
        }
        Team savedTeam = teamRepository.save(team);

        if (savedTeam == null) {
            return "팀 생성에 실패하였습니다";
        }
  
        Boolean isallMemberSaved = teamMemberService.saveTeamMember(savedTeam, createTeamRequestDto.getUserNNList());

        if (!isallMemberSaved) {
            return "팀원 등록에 실패하였습니다";
        }

        return "팀이 생성되었습니다";

    }

    @Transactional
    public String deleteTeam(String teamName, Long userPk) {

        Team team = teamRepository.findByTeamName(teamName);
        if (team == null) {
            return "존재하지 않는 팀을 삭제하려 하였습니다.";
        }

        if(team.getTeamLeaderPk() != userPk){
            return "사용자가 팀장이 아닙니다.";
        }

        teamRepository.delete(team);
        return "팀이 삭제되었습니다.";
    }

    @Transactional
    public Team getTeamInfo(String teamName, Long userPk) {
        Team team = teamRepository.findByTeamName(teamName);
        
        if (team == null) {
            log.info("존재하지 않는 팀을 조회하려 하였습니다.");
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
        log.info("팀에 속하지 않은 사용자가 팀 정보를 조회하려 하였습니다.");
        return null;
    }
        
    

}

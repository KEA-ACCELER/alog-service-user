package kea.alog.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kea.alog.user.domain.team.Team;
import kea.alog.user.domain.team.TeamRepository;
import kea.alog.user.domain.teamMember.TeamMember;
import kea.alog.user.domain.teamMember.TeamMemberRepository;
import kea.alog.user.domain.user.User;
import kea.alog.user.domain.user.UserRepository;
import kea.alog.user.web.dto.TeamMemberDto.AddTeamMemberRequestDto;
import kea.alog.user.web.dto.TeamMemberDto.DeleteTeamMembersRequestDto;
import kea.alog.user.web.dto.TeamMemberDto.GetTeamMembersInfoResponseDto;
import kea.alog.user.web.dto.TeamMemberDto.SaveTeamMembersRequestDto;
import kea.alog.user.web.dto.TeamMemberDto.getTeamMembersResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamMemberService {

    @Autowired
    TeamMemberRepository teamMemberRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public boolean saveTeamMember(Team savedTeam, List<String> NNList) {
        log.info("NNList: " + NNList.toString());
        // ArrayList<String> userNNList = new ArrayList<>(NNList);
        for (String userNN : NNList) {
            User user = userRepository.findByUserNn(userNN);
            System.out.println("user: " + user);
            if (user == null || user.isUserDeleted()) {
                log.info("There is no user to add");
                return false;
            }

            if (savedTeam.getTeamLeaderPk() == user.getUserPk()) {
                log.info("Team leader cannot be team member");
                return false;
            }

            if (teamMemberRepository.existsByTeamAndUser(savedTeam, user)) {
                log.info("The user is already team member");
                return false;
            }

            TeamMember teamMember = teamMemberRepository.save(TeamMember.builder()
                    .team(savedTeam)
                    .user(user)
                    .build());
            if (teamMember == null) {
                return false;
            }
        }
        return true;
    }

    @Transactional
    public String addTeamMember(AddTeamMemberRequestDto addTeamMemberRequestDto, Long userPk) {

        Team team = teamRepository.findByTeamPk(addTeamMemberRequestDto.getTeamPk());
        if (team == null) {
            return "The team is not existed";
        }
        if (team.getTeamLeaderPk() != userPk) {
            return "You are not the leader of the team";
        }
        Boolean isAllMemberSaved = saveTeamMember(team, addTeamMemberRequestDto.getUserNNList());
        if (!isAllMemberSaved) {
            return "Team member is not added";
        }
        return "More than one team member is added";

    }

    @Transactional
    public String deleteTeamMember(DeleteTeamMembersRequestDto deleteTeamMembersRequestDto, Long userPk) {
        if (deleteTeamMembersRequestDto.getUserNNList() == null) {
            return "There is no user to delete";
        }
        Team team = teamRepository.findByTeamPk(deleteTeamMembersRequestDto.getTeamPk());
        if (team == null) {
            return "The team is not existed";
        }

        if (userPk != team.getTeamLeaderPk()) {
            return "You are not team leader.";
        }
        

        for (String userNN : deleteTeamMembersRequestDto.getUserNNList()) {
            User user = userRepository.findByUserNn(userNN);
            if (user == null || user.isUserDeleted()) {
                return "You can not delete the user who is not existed or deleted.";
            }
            if (user.getUserPk()== userPk){
                return "You can not delete yourself.";
            }
            teamMemberRepository.deleteByTeamAndUser(team, user);
        }
        return "More than one team member is deleted";
    }

    @Transactional
    public getTeamMembersResponseDto getTeamMembers(Long teamPk, Long userPk) {
        Team team = teamRepository.findByTeamPk(teamPk);
        if (team == null) {
            log.info("Team is not existed");
            return null;
        }

        User user = userRepository.findByUserPk(userPk);
        if (user == null || user.isUserDeleted()) {
            log.info("User is not existed");
            return null;
        }

        if (!teamMemberRepository.existsByTeamAndUser(team, user) && team.getTeamLeaderPk() != userPk) {
            log.info("You are not team member.");
            return null;
        }

        ArrayList<String> teamMemberNNs = new ArrayList<>();
        for (TeamMember teamMember : teamMemberRepository.findAllByTeam(team)) {
            teamMemberNNs.add(teamMember.getUser().getUserNn());
        }

        User teamLeader = userRepository.findByUserPk(team.getTeamLeaderPk());
        return getTeamMembersResponseDto.builder()
                .teamLeaderNN(teamLeader.getUserNn())
                .teamMemberNNs(teamMemberNNs)

                .build();

    }

    @Transactional
    public List<GetTeamMembersInfoResponseDto> getTeamMembersInfo(Long teamPk) {
        Team team = teamRepository.findByTeamPk(teamPk);
        if (team == null) {
            log.info("Team is not existed");
            return null;
        }

        List<TeamMember> teamMemberList = teamMemberRepository.findAllByTeam(team);
        if (teamMemberList == null) {
            log.info("Team member is not existed");
            return null;
        }

        List<GetTeamMembersInfoResponseDto> teamMembersInfo = teamMemberList.stream()
                .map(teamMember -> new GetTeamMembersInfoResponseDto(teamMember.getUser().getUserPk(),
                        teamMember.getUser().getUserNn(),
                        teamMember.getUser().getUserEmail()))
                .collect(Collectors.toList());

        User teamLeader = userRepository.findByUserPk(team.getTeamLeaderPk());

        teamMembersInfo.add(GetTeamMembersInfoResponseDto.builder()
                .userPk(teamLeader.getUserPk())
                .userNn(teamLeader.getUserNn())
                .userEmail(teamLeader.getUserEmail())
                .build());

        return teamMembersInfo;




    }

}

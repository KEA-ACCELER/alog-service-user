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
import kea.alog.user.web.dto.TeamMemberDto.DeleteTeamMembersRequestDto;
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
        System.out.println("NNList: " + NNList.toString());
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
    public String addTeamMember(SaveTeamMembersRequestDto saveTeamMembersRequestDto, Long userPk) {

        Team team = teamRepository.findByTeamName(saveTeamMembersRequestDto.getTeamName());
        if (team == null) {
            return "The team is not existed";
        }
        if (team.getTeamLeaderPk() != userPk) {
            return "You are not the leader of the team";
        }
        Boolean isAllMemberSaved = saveTeamMember(team, saveTeamMembersRequestDto.getUserNNList());
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
        Team team = teamRepository.findByTeamName(deleteTeamMembersRequestDto.getTeamName());
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
    public getTeamMembersResponseDto getTeamMembers(String teamName, Long userPk) {
        Team team = teamRepository.findByTeamName(teamName);
        if (team == null) {
            log.info("존재하지 않는 팀입니다");
            return null;
        }
        if (!teamMemberRepository.existsByTeamAndUser(team, userRepository.findByUserPk(userPk)) && team.getTeamLeaderPk() != userPk) {
            log.info("팀 멤버가 아닙니다.");
            return null;
        }
        ArrayList<String> teamMemberNNs = new ArrayList<>();
        for (TeamMember teamMember : teamMemberRepository.findAllByTeam(team)) {
            teamMemberNNs.add(teamMember.getUser().getUserNn());
        }

        User teamLeader = userRepository.findByUserPk(team.getTeamLeaderPk());
        //String[] arr = teamMemberNNs.stream().toArray(String[]::new); 
        return getTeamMembersResponseDto.builder()
                .teamLeaderNN(teamLeader.getUserNn())
                .teamMemberNNs(teamMemberNNs)
                .build();

    }

}

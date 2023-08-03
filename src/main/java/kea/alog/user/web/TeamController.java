package kea.alog.user.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import kea.alog.user.domain.team.Team;
import kea.alog.user.domain.teamMember.TeamMember;
import kea.alog.user.service.TeamService;
import kea.alog.user.web.dto.TeamDto;


@RequestMapping("/api/users/teams")
@RestController
public class TeamController {
    
    @Autowired
    TeamService teamService;

    @Operation(summary = "팀 생성", description = "새로운 팀 생성과 동시에 팀 멤버를 등록, 같은 유저가 같은 이름의 팀을 만들 경우 생성 불가, 팀 이름만 같을 경우 생성 가능, 팀장은 팀 멤버가 아니므로 팀 멤버의 리스트에 넣지 말 것.")
    @PostMapping()
    public ResponseEntity<String> createTeam(@RequestBody TeamDto.CreateTeamRequestDto createTeamRequestDto, @RequestParam Long userPk) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teamService.createTeam(createTeamRequestDto, userPk));
    }
    
    @Operation(summary = "팀 삭제", description = "기존에 있던 팀을 삭제(팀 리더 권한)")
    @DeleteMapping()
    public ResponseEntity<String> deleteTeam(@RequestParam Long teamPk, @RequestParam Long userPk) {
        return ResponseEntity.status(HttpStatus.OK).body(teamService.deleteTeam(teamPk, userPk));
    }

    @Operation(summary = "팀 정보", description = "팀pk로 팀 정보 조회")
    @GetMapping()
    public ResponseEntity<Team> getTeamInfo(@RequestParam Long teamPk, @RequestParam Long userPk) {
        return ResponseEntity.status(HttpStatus.OK).body(teamService.getTeamInfo(teamPk, userPk));
    }

    @Operation(summary = "속한 팀 리스트", description = "userPk를 이용하여 속한 팀 리스트 나열" )
    @GetMapping("/list")
    public ResponseEntity<List<Team>> getJoinedTeamList(@RequestParam Long userPk){
        return ResponseEntity.ok(teamService.getJoinedTeamList(userPk));
    }
}

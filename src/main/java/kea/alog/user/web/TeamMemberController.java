package kea.alog.user.web;

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
import io.swagger.v3.oas.annotations.Parameter;
import kea.alog.user.service.TeamMemberService;

import kea.alog.user.web.dto.TeamMemberDto.DeleteTeamMembersRequestDto;
import kea.alog.user.web.dto.TeamMemberDto.SaveTeamMembersRequestDto;
import kea.alog.user.web.dto.TeamMemberDto.getTeamMembersResponseDto;

@RequestMapping("/api/users/team-members")
@RestController
public class TeamMemberController {

    @Autowired
    TeamMemberService teamMemberService;

    @Operation(summary = "팀 멤버 한 명 이상 등록", description = "기존에 있던 팀에 신규 멤버를 등록")
    @PostMapping()
    public ResponseEntity<String> saveTeamMember(@RequestBody SaveTeamMembersRequestDto saveTeamMembersResquestDto,
            @RequestParam Long userPk) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(teamMemberService.addTeamMember(saveTeamMembersResquestDto, userPk));
    }

    @Operation(summary = "팀 멤버 한 명 이상삭제", description = "기존에 있던 팀에 멤버를 삭제 (팀 리더 권한)")
    @DeleteMapping()
    public ResponseEntity<String> deleteTeamMember(@RequestBody DeleteTeamMembersRequestDto deleteTeamMembersRequestDto,
            @RequestParam Long userPk) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(teamMemberService.deleteTeamMember(deleteTeamMembersRequestDto, userPk));
    }

    @Operation(summary = "팀의 멤버 닉네임 나열", description = "팀장과 팀의 멤버를 나열, 잘못된 조건으로 입력할 경우 null값 반환")
    @GetMapping()
    public ResponseEntity<getTeamMembersResponseDto> getTeamMembers(@RequestParam String teamName,
            @Parameter(description = "본인이 속한 팀인지 확인하는 용도") @RequestParam Long userPk) {

        return ResponseEntity.status(HttpStatus.OK).body(teamMemberService.getTeamMembers(teamName, userPk));
    }

}

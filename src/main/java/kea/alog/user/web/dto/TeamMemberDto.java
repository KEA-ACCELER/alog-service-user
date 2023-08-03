package kea.alog.user.web.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TeamMemberDto {

    // TODO UserPk 써서 본인인증 하는 부분을 헤더에서 가지고 와야함.

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SaveTeamMembersRequestDto {
        private String teamName;
        private List<String> userNNList;

        @Builder
        public SaveTeamMembersRequestDto(String teamName, List<String> userNNList){
            this.teamName = teamName;
            this.userNNList = userNNList;
        }
    }
    
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AddTeamMemberRequestDto{
        private Long teamPk;
        private List<String> userNNList;

        @Builder
        public AddTeamMemberRequestDto(Long teamPk, List<String> userNNList){
            this.teamPk = teamPk;
            this.userNNList = userNNList;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DeleteTeamMembersRequestDto {
        private Long teamPk;
        private List<String> userNNList;

        @Builder
        public DeleteTeamMembersRequestDto(Long teamPk, List<String> userNNList){
            this.teamPk = teamPk;
            this.userNNList = userNNList;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class getTeamMembersResponseDto {
        private String teamLeaderNN;
        private List<String> teamMemberNNs;

        @Builder
        public getTeamMembersResponseDto(String teamLeaderNN, List<String> teamMemberNNs){
            this.teamLeaderNN = teamLeaderNN;
            this.teamMemberNNs = teamMemberNNs;
        }
    }

}

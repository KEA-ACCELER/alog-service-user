package kea.alog.user.web.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class TeamDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    public static class CreateTeamRequestDto {
        private String teamName;
        private List<String> userNNList;


    }
}
package kea.alog.user.web.dto;

import java.time.LocalDateTime;

import kea.alog.user.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RegistRequestDto {
        private String userId;
        @Setter
        private String userPw;
        private String userNN;
        private String email;
        
        public User toEntity() {
            return User.builder()
                    .userId(userId)
                    .userPw(userPw)
                    .userNn(userNN)
                    .userEmail(email)
                    .userDeleted(false) //default value
                    .build();
        }
    }
    
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetUserResponseDto {
        private Long userPk;
        private String userId ;
        private String userPw;
        private String email;
        private String name;
        private String phone;
        private String address;
        private LocalDateTime birth;
        private Boolean gender;

        @Builder
        public GetUserResponseDto(Long userPk,String userId, String userPw, String email, String name, String phone, String address, LocalDateTime birth, Boolean gender){
            this.userPk = userPk;
            this.userId = userId;
            this.userPw = userPw;
            this.email = email;
            this.phone = phone;
            this.address = address;
            this.birth = birth;
            this.gender = gender;
            this.name = name;
        }
    }
    
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateProfileRequestDto {
        private String userId;
        private String userProfile;

        @Builder
        public UpdateProfileRequestDto(String userId, String profile){
            this.userId = userId;
            this.userProfile = profile;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LoginRequestDto{
        private String userId;
        private String userPw;
    }
}
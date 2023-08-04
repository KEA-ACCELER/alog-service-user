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
        @Setter
        private String userPw;
        private String userNN;
        private String email;
        
        public User toEntity() {
            return User.builder()
                    .userPw(userPw)
                    .userNn(userNN)
                    .userEmail(email)
                    .userDeleted(false) //default value
                    .isVerified(true)
                    .build();
        }
    }
    
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetUserResponseDto {
        
        private Long userPk;
        // private String userPw;
        private String email;
        private String userNN;
        // TODO 유저 프로필 이미지 추가


        @Builder
        public GetUserResponseDto(Long userPk,  String email, String NN){
            this.userPk = userPk;
            // this.userPw = userPw지
            this.email = email;
            this.userNN = NN;
        }
    }
    
    // @Getter
    // @NoArgsConstructor(access = AccessLevel.PROTECTED)
    // public static class UpdateProfileRequestDto {
    //     private String userId;
    //     private String userProfile;

    //     @Builder
    //     public UpdateProfileRequestDto(String userId, String profile){
    //         this.userId = userId;
    //         this.userProfile = profile;
    //     }
    // }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LoginRequestDto{
        private String userEmail;
        private String userPw;

        @Builder
        public LoginRequestDto(String userEmail, String userPw){
            this.userEmail = userEmail;
            this.userPw = userPw;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LoginResponseDto{
        private Long userPk;
        private String userNN;

        @Builder
        public LoginResponseDto(Long userPk, String userNN){
            this.userPk = userPk;
            this.userNN = userNN;
        }
    }
}
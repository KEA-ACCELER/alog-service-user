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
                    .userProfile("")
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class VerifiedRegistRequestDto{
        @Setter
        private String userPw;
        private String userNN;
        private String email;


        public User toEntity(){
            return User.builder()
                    .userPw(userPw)
                    .userNn(userNN)
                    .userEmail(email)
                    .userDeleted(false) //default value
                    .isVerified(true)
                    .userProfile("")
                    .build();
        }
    }
    
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetUserResponseDto {
        
        private Long userPk;
        private String email;
        private String userNN;
        private String userProfile;


        @Builder
        public GetUserResponseDto(Long userPk,  String email, String NN, String userProfile){
            this.userPk = userPk;
            this.email = email;
            this.userNN = NN;
            this.userProfile = userProfile;
        }
    }
    

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
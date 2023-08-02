package kea.alog.user.web.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


public class EmailDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class EmailMessage {
        private String to; // 수신자
        private String subject; // 메일 제목
        private String message; // 메일 내용

        @Builder
        public EmailMessage(String to, String subject, String message) {
            this.to = to;
            this.subject = subject;
            this.message = message;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class VerifyEmailRequestDto{
        private String email;
        private String code;

        @Builder
        public VerifyEmailRequestDto(String email, String code){
            this.email = email;
            this.code = code;
        }
    }

}
package kea.alog.user.domain.email;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailMessage {

    private String to; // 수신자
    private String subject; //메일 제목
    private String message; //메일 내용

    @Builder
    public EmailMessage(String to, String subject, String message){
        this.to = to;
        this.subject = subject;
        this.message = message;
    }
}
package kea.alog.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kea.alog.user.domain.email.EmailMessage;
import kea.alog.user.utils.CreateRandomCode;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public String sendMimeMessage(EmailMessage emailMessage) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
        
        helper.setTo(emailMessage.getTo());
        helper.setSubject(emailMessage.getSubject());
        helper.setText(emailMessage.getMessage(), false);
        
        mailSender.send(message);

        return "코드 전송을 성공적으로 진행하였습니다";
    }

    
}

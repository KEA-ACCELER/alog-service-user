package kea.alog.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import kea.alog.user.domain.email.Email;
import kea.alog.user.domain.email.EmailRepository;
import kea.alog.user.domain.user.UserRepository;
import kea.alog.user.utils.CreateRandomCode;
import kea.alog.user.web.dto.EmailDto;
import kea.alog.user.web.dto.EmailDto.EmailMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private UserRepository userRepository;

    // 이메일 인증코드 발송
    @Transactional
    public String sendEmail(String emailTo) {

        if (emailTo == null) {
            return "Please Enter Email Address";
        }

        if (userRepository.findByUserEmail(emailTo) != null) {
            return "You are already signed up";
        }

        // 인증 코드 발급
        String authCode = CreateRandomCode.createCode();

        Email email = emailRepository.findByEmail(emailTo);
        if (email != null) {
            email.setVerifyCode(authCode);
            EmailDto.EmailMessage message = EmailDto.EmailMessage.builder()
                    .to(emailTo)
                    .subject("A-Log 인증 코드가 재발송되었습니다")
                    .message("이전의 발송된 코드는 무효화 처리되었습니다. 아래의 인증코드를 복사해서 붙여넣기해주세요 \n\n 인증 코드: " + authCode)
                    .build();

            sendMimeMessage(message);
            return "Email Verify Code re-sended Successfully";
        } else {
            log.info("이메일 최초인증");
            email = emailRepository.save(Email.builder()
                    .email(emailTo)
                    .verifyCode(authCode)
                    .build());
            EmailDto.EmailMessage message = EmailDto.EmailMessage.builder()
                    .to(emailTo)
                    .subject("A-Log 인증 코드가 발송되었습니다")
                    .message("아래의 인증코드를 복사해서 붙여넣기해주세요 \n\n 인증 코드: " + authCode)
                    .build();

            sendMimeMessage(message);
            return "Email Verify Code sended Successfully";
        }

    }

    @Transactional
    public String verifyEmail(EmailDto.VerifyEmailRequestDto verifyEmailRequestDto) {

        Email email = emailRepository.findByEmail(verifyEmailRequestDto.getEmail());
        if (email == null) {
            return "Email is not existed. Please try again";
        }
        if (email.getVerifyCode().equals(verifyEmailRequestDto.getCode())) {
            email.setVerifyCode("VERIFIED");
            return "Email is Verified Successfully";
        }

        return "Email is not Verified. Please try again";
    }

    public void sendMimeMessage(EmailMessage messageContent) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setTo(messageContent.getTo());
            helper.setSubject(messageContent.getSubject());
            helper.setText(messageContent.getMessage(), false);

            mailSender.send(message);
        } catch (MessagingException e) {
            log.info("Sending Email is failed");
        }

    }
}

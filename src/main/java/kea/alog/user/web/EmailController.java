package kea.alog.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import kea.alog.user.service.EmailService;
import kea.alog.user.web.dto.EmailDto;

@RestController
@RequestMapping("/api/users/emails")
public class EmailController {

    @Autowired
    private EmailService emailService;

    
    /*
     * 이메일 인증코드 발송
     * 
     * @variable EmailTo
     * 
     * @return ok, "success"
     */
    @Operation(summary = "인증 메일 전송", description = "인증 메일 전송")
    @PostMapping(path = "/send")
    public ResponseEntity<String> sendEmail(@RequestParam String EmailTo) {
        return ResponseEntity.ok(emailService.sendEmail(EmailTo));
    }

    /*
     * 이메일 인증코드 확인
     * 
     * @variable EmailTo, code
     *송
     * @return ok, "success"
     */

    @Operation(summary = "이메일 인증 확인", description = "이메일 인증 확인")
    @PostMapping(path = "/verify", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> verifyEmail(@RequestBody EmailDto.VerifyEmailRequestDto verifyEmailRequestDto) {
        return ResponseEntity.ok(emailService.verifyEmail(verifyEmailRequestDto));
    }
}

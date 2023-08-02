package kea.alog.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import kea.alog.user.domain.email.EmailMessage;
import kea.alog.user.domain.user.User;
import kea.alog.user.domain.user.UserRepository;
import kea.alog.user.utils.CreateRandomCode;
import kea.alog.user.web.dto.UserDto;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@NoArgsConstructor
@Slf4j
public class UserService {
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;

    // 회원등록
    @Transactional
    public User signUp(UserDto.RegistRequestDto registRequestDto) {
        registRequestDto.setUserPw(passwordEncoder.encode(registRequestDto.getUserPw()));
        return userRepository.save(registRequestDto.toEntity());

    } 
    
   // 로그인
    @Transactional
    public UserDto.LoginResponseDto userLogin(UserDto.LoginRequestDto loginRequestDto) {
        User user = userRepository.findByUserEmail(loginRequestDto.getUserEmail());
        if (user == null) {
            return null;
        }
        if (!passwordEncoder.matches(loginRequestDto.getUserPw(), user.getUserPw())) {// 평문, 암호화 순. 순서 유의
            return null;
        }
        
        return UserDto.LoginResponseDto.builder()
                .userPk(user.getUserPk())
                .userNN(user.getUserNn())
                .build();
    }

    // 회원정보조회
    @Transactional
    public UserDto.GetUserResponseDto getUser(Long userPk) {

        User user = userRepository.findByUserPk(userPk);
        return UserDto.GetUserResponseDto.builder()
                .userPk(user.getUserPk())
                .email(user.getUserEmail())
                .NN(user.getUserNn())
                .build();

    }

    // 회원 삭제
    @Transactional
    public void deleteUser(Long userPk) {
        User user = userRepository.findByUserPk(userPk);
        user.setUserDeleted(true);
    }

    // 아이디 중복 확인
    @Transactional
    public boolean isDuplicatedId(String userNN) {
        return userRepository.existsByUserNn(userNN);
    }

    //이메일 인증코드 발송
    @Transactional
    public String verifyingEmail(String emailTo) {
        String authCode = CreateRandomCode.createCode();

        EmailMessage message = EmailMessage.builder()
                                            .to(emailTo)
                                            .subject("회원가입 인증 코드")
                                            .message("인증 코드: " + authCode)
                                            .build();

        String result = emailService.sendMimeMessage(message);
        
        return null;
    }

    //이메일 인증코드 대조
    @Transactional
    public boolean isEmailVerified(String authCode) {
        return false;
    }
}

package kea.alog.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import jakarta.transaction.Transactional;
import kea.alog.user.domain.email.Email;
import kea.alog.user.domain.email.EmailRepository;
import kea.alog.user.domain.user.User;
import kea.alog.user.domain.user.UserRepository;
import kea.alog.user.utils.CreateRandomCode;
import kea.alog.user.web.dto.EmailDto;
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
    EmailRepository emailRepository;

    // 회원등록
    // TODO 깃허브로 접근 할 경우 이메일 인증 하지 않음.
    @Transactional
    public User signUp(UserDto.RegistRequestDto registRequestDto) {
        if (userRepository.findByUserEmail(registRequestDto.getEmail()) != null) {
            log.info("이미 가입된 이메일입니다");
            return null;
        }

        Email email = emailRepository.findByEmail(registRequestDto.getEmail());
        if(email==null){
            log.info("이메일 인증을 해주세요");
            return null;
        }
        if (!email.getVerifyCode().equals("VERIFIED")) {
            log.info("이메일 인증을 다시 시도해주세요");
            return null;
        }
    
        //회원 가입을 완료한 유저이기 때문에 email테이블의 정보 삭제
        emailRepository.delete(email);

        //비밀번호 암호화
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


}

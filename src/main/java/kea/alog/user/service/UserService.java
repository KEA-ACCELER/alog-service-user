package kea.alog.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kea.alog.user.domain.user.User;
import kea.alog.user.domain.user.UserRepository;
import kea.alog.user.util.JwtUtil;
import kea.alog.user.web.dto.UserDto;
import kea.alog.user.web.dto.UserDto.LoginRequestDto;
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

    @Value("${jwt.secret}")
    private String secretKey;

    private Long expireMS = 1000L * 60 * 60 * 24 * 7;// 1주일

    // 회원등록
    @Transactional
    public User signUp(UserDto.RegistRequestDto registRequestDto) {
        registRequestDto.setUserPw(passwordEncoder.encode(registRequestDto.getUserPw()));
        return userRepository.save(registRequestDto.toEntity());

    } 
    
   // 로그인
    @Transactional
    public String userLogin(UserDto.LoginRequestDto loginRequestDto) {
        User user = userRepository.findByUserId(loginRequestDto.getUserId());
        if (user == null) {
            return "아이디가 존재하지 않습니다.";
        }
        if (!passwordEncoder.matches(loginRequestDto.getUserPw(), user.getUserPw())) {// 평문, 암호화 순. 순서 유의
            return "비밀번호가 일치하지 않습니다.";
        }
        
        String token = JwtUtil.createJwt(user.getUserPk(), user.getUserId(), secretKey, expireMS);
        // System.out.println("token  " + token);
        return token;
    }

    // 회원정보조회
    @Transactional
    public UserDto.GetUserResponseDto getUser(Authentication authentication) {

        User user = userRepository.findByUserId(authentication.getName());
        log.info(authentication.getDetails().toString());
        return UserDto.GetUserResponseDto.builder()
                .userPk(user.getUserPk())
                .userId(user.getUserId())
                .userPw(user.getUserPw())
                .email(user.getUserEmail())
                .NN(user.getUserNn())
                .build();

    }

    // 회원 삭제
    // @Transactional
    // public void deleteUser(Authentication authentication) {
    //     userRepository.deleteByUserId(authentication.getName());
    // }

    // 아이디 중복 확인
    @Transactional
    public boolean isDuplicatedId(String userId) {
        return userRepository.existsByUserId(userId);
    }
}

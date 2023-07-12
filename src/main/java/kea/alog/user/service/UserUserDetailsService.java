package kea.alog.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kea.alog.user.domain.PrincipalDetails;
import kea.alog.user.domain.user.User;
import kea.alog.user.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserUserDetailsService implements UserDetailsService{
    @Autowired
    UserRepository userRepository;

    //security session -> Authentication -> UserDetails(내부 PrincipalDetails)
    /**
     * username을 가지고 사용자 정보를 조회
        사용자의 Role과 권한(Privilege)을 조회하여, SimpleGrantedAuthority 목록을 authorities에 세팅한다.
        Authentication 내부 principal 객체에 UserDetails 객체가 저장된다.
        Authentication 내부 authorities 객체에 사용자의 Role과 권한(Privilege) 정보가 저장된다.
        UserDetails에 authorities가 세팅되어 있어야, API별 role이나 권한 체크를 진행할 수 있다.
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        log.info("UserUserDetailsService loadUserByUsername : "+userId);
        User user =userRepository.findByUserId(userId);
        return new PrincipalDetails(user);
    }
}

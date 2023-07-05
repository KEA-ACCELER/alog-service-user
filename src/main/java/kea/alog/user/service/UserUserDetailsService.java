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

@Service
@RequiredArgsConstructor
public class UserUserDetailsService implements UserDetailsService{
    @Autowired
    UserRepository userRepository;

    //security session -> Authentication -> UserDetails

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user =userRepository.findByUserId(userId);
        return new PrincipalDetails(user);
    }
}

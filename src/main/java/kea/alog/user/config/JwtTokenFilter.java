package kea.alog.user.config;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import kea.alog.user.domain.user.UserRepository;
import kea.alog.user.service.UserService;
import kea.alog.user.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter{ // 모든 요청마다 토큰을 검사해야하니까 OncePerRequestFilter를 상속받는다.
    @Autowired 
    private final UserService userService;
    
   
    private final String secretKey;

    @Autowired
    UserRepository userRepository;

    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException { 

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION); // 헤더에서 토큰을 꺼내온다. 
        log.info("authorization : "+authorization);

        if (authorization == null || !authorization.startsWith("Bearer ")){ // 토큰이 없으면 권한부여 하지 않기

            log.error(" WARN : authenticationHeader is null");
            filterChain.doFilter(request, response);
            return;
        }
        
        //Token 꺼내기
        String token = authorization.split(" ")[1]; 
        
        //Token Expired 여부
        if (JwtUtil.isExpired(token, secretKey)) {
            log.error(" ERROR : Token Expired");
            filterChain.doFilter(request, response);
            return;
        }
        // Token에서 유저 정보 꺼내기
        String userName = JwtUtil.getUserId(token, secretKey); 
        //Long userPk = Long.parseLong(JwtUtil.getUserPk(token, secretKey));
        //User user = userRepository.findByUserPk(userPk);

        log.info("userName : "+userName);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userName, null, List.of(new SimpleGrantedAuthority("USER"))); //권한 부여

        //Detail을 넣기
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);

    }

}

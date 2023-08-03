package kea.alog.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import feign.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import kea.alog.user.domain.user.User;
import kea.alog.user.service.UserService;
import kea.alog.user.web.dto.UserDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    UserService userService;

    @Operation(summary = "회원가입", description = "회원가입")
    @ApiResponse(responseCode = "201", description = "return : userNn \n 사용예시 : userNn님 환영합니다")
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDto.RegistRequestDto registRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(registRequestDto).getUserNn());
    }
    /*
     * 로그인 API
     * 
     * @RequestBody UserDto.LoginRequestDto
     * 
     * @return ok
     */
    @PostMapping("/login")
    public ResponseEntity<UserDto.LoginResponseDto> login(@RequestBody UserDto.LoginRequestDto loginRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.userLogin(loginRequestDto));
    }

    /*
     * 아이디 중복확인 API
     * 
     * @variable userId
     * 
     * @return ok
     */
    @Operation(summary = "아이디 중복확인", description = "아이디 중복확인")
    @GetMapping(path = "/duplicated/{userNN}")
    public ResponseEntity<Boolean> isDuplicatedId(@PathVariable(value = "userNN") String userNN) {
        return ResponseEntity.ok(userService.isDuplicatedId(userNN));
    }

    /*
     * 회원탈퇴 API
     * 
     * @variable userPk
     * 
     * @return ok, "deleted"
     */
    @DeleteMapping(path = "/delete/{userPk}")
    public ResponseEntity<String> deleteUser(@PathVariable(value = "userPk") Long userPk) {
        userService.deleteUser(userPk);
        return ResponseEntity.ok("deleted");
    }

    /*
     * 회원조회 API
     * 
     * @variable userId
     * 
     * @return ok, userDto.GetUserResponseDto
     */
    @GetMapping(path = "/info/{userPk}")
    public ResponseEntity<UserDto.GetUserResponseDto> getUserInfo(@PathVariable(value = "userPk") Long userPk) {
        return ResponseEntity.ok(userService.getUser(userPk));
    }

 

}

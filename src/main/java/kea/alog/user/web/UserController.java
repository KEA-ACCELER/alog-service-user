package kea.alog.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    UserService userService;

    @Operation(summary = "회원가입", description = "회원가입")
    @ApiResponse(responseCode = "201", description = "return 사용예시 : userNn님 환영합니다")
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDto.RegistRequestDto registRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(registRequestDto).getUserNn());
    }

    // @GetMapping("/login")
    // public ResponseEntity<User> login(@RequestBody UserDto.LoginRequestDto loginRequestDto) {
    //     return ResponseEntity.status(HttpStatus.OK).body(userService.userLogin(loginRequestDto));
    // }

    /*
     * 아이디 중복확인 API
     * 
     * @variable userId
     * 
     * @return ok
     */
    @GetMapping(path = "/idCheck/{userId}")
    public ResponseEntity<Boolean> isDuplicatedId(@PathVariable(value = "userId") String userId) {
        return ResponseEntity.ok(userService.isDuplicatedId(userId));
    }

    /*
     * 회원탈퇴 API
     * 
     * @variable userPk
     * 
     * @return ok, "deleted"
     */
    // @DeleteMapping(path = "/delete")
    // public ResponseEntity<String> deleteUser(Authentication authentication) {
    //     userService.deleteUser(authentication);
    //     return ResponseEntity.ok("deleted");
    // }

    /*
     * 회원조회 API
     * 
     * @variable userId
     * 
     * @return ok, userDto.GetUserResponseDto
     */
    // @GetMapping(path = "/info")
    // public ResponseEntity<UserDto.GetUserResponseDto> getUserInfo(Authentication authentication) {
    //     return ResponseEntity.ok(userService.getUser(authentication));
    // }
}

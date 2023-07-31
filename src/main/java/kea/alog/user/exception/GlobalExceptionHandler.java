package kea.alog.user.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // @ExceptionHandler(MissingServletRequestParameterException.class)
    // protected ResponseEntity<ResponseDto> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
    //     log.error("handleMissingServletRequestParameterException");
    //     return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    //             .body(ResponseDto.fail(400, "PROPERTY_REQUIRED"));
    // }

    // @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    // protected ResponseEntity<ResponseDto> handleMethodArgumentTypeMismatchException(
    //         MethodArgumentTypeMismatchException e) {
    //     log.error("MethodArgumentTypeMismatchException");
    //     return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    //             .body(ResponseDto.fail(400, "INVALID_PROPERTY"));
    // }
}
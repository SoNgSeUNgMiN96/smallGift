package com.sgwannabig.smallgift.springboot.controller.exception;

import com.sgwannabig.smallgift.springboot.config.advice.exception.*;
import com.sgwannabig.smallgift.springboot.service.ResponseService;
import com.sgwannabig.smallgift.springboot.service.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionAdvice {

  private final ResponseService responseService;

  @ExceptionHandler(MemberEmailAlreadyExistsException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Result userEmailAlreadyExistsException() {
    return responseService.getFailureResult(-101, "이미 존재하는 이메일입니다.");
  }

  @ExceptionHandler(LoginFailureException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Result loginFailureException() {
    return responseService.getFailureResult(-102, "아이디 혹은 비밀번호가 틀립니다.");
  }

  @ExceptionHandler(AuthenticationEntryPointException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Result authenticationEntryPointException() {
    return responseService.getFailureResult(-102, "인증이 필요합니다.");
  }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Result accessDeniedException() {
    return responseService.getFailureResult(-103, "권한이 필요합니다.");
  }

  @ExceptionHandler(InvalidRefreshTokenException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Result invalidRefreshTokenException() {
    return responseService.getFailureResult(-104, "Refresh Token이 유효하지 않습니다.");
  }

  @ExceptionHandler(EmailNotAuthenticatedException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Result emailAuthenticationException() {
    return responseService.getFailureResult(-105, "이메일 인증이 필요합니다.");
  }

  @ExceptionHandler(EmailAuthTokenNotFoundException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Result emailAuthTokenNotFountException(EmailAuthTokenNotFoundException e) {
    log.error("Exception: {}, message: {}", e.getClass(), e.getMessage());
    return responseService.getFailureResult(-106, "유효하지 않은 인증요청입니다.");
  }

  @ExceptionHandler(ShopNotFoundException.class)
  public ResponseEntity<Result> shopNotFoundException(ShopNotFoundException e) {
    log.error("Exception: {}, message: {}", e.getClass(), e.getMessage());
    return new ResponseEntity<>(responseService.getFailureResult(40400, e.getMessage()),
        HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity<Result> productNotFoundException(ProductNotFoundException e) {
    log.error("Exception: {}, message: {}", e.getClass(), e.getMessage());
    return new ResponseEntity<>(responseService.getFailureResult(40400, e.getMessage()),
        HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ManagerExistedException.class)
  public ResponseEntity<Result> managerExistedException(ManagerExistedException e) {
    log.error("Exception: {}, message: {}", e.getClass(), e.getMessage());
    return new ResponseEntity<>(responseService.getFailureResult(40000, e.getMessage()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ManagerNotFoundException.class)
  public ResponseEntity<Result> managerNotFoundException(ManagerNotFoundException e) {
    log.error("Exception: {}, message: {}", e.getClass(), e.getMessage());
    return new ResponseEntity<>(responseService.getFailureResult(40400, e.getMessage()),
        HttpStatus.NOT_FOUND);
  }
}
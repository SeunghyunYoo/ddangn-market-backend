package com.ddangnmarket.ddangmarkgetbackend.exception.advice;

import com.ddangnmarket.ddangmarkgetbackend.domain.account.exception.DuplicateEmailException;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.exception.DuplicateNicknameException;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.exception.SignUpException;
import com.ddangnmarket.ddangmarkgetbackend.exception.dto.ErrorResult;
import com.ddangnmarket.ddangmarkgetbackend.exception.dto.ValidError;
import com.ddangnmarket.ddangmarkgetbackend.exception.dto.ValidationErrorMessage;
import com.ddangnmarket.ddangmarkgetbackend.utils.repository.IllegalSortArgumentException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    private final ObjectMapper objectMapper = new ObjectMapper();
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(SignUpException.class)
//    public ErrorResult signUpExHandler(SignUpException e){
//        log.error("[exceptionHandler]", e);
//        return new ErrorResult("SC_BAD_REQUEST", e.getMessage());
//    }

    @ExceptionHandler({IllegalArgumentException.class, SignUpException.class, IllegalStateException.class,
            DuplicateEmailException.class, DuplicateNicknameException.class})
    public ResponseEntity<ErrorResult> signUpExHandler(Exception e) {
        log.error("[exceptionHandler]", e);
//        log.error("[exceptionHandler] [{}]",handlerMethod, e);
        ErrorResult errorResult = new ErrorResult(HttpStatus.BAD_REQUEST.name(),
                HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResult> methodArgumentNotExHandler(MethodArgumentNotValidException e) throws JsonProcessingException {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        ValidationErrorMessage validationErrorMessage = new ValidationErrorMessage();
        List<ValidError> validationErrors = validationErrorMessage.getValidationErrors();

        for (FieldError fieldError : fieldErrors) {
            String defaultMessage = fieldError.getDefaultMessage();
            String field = fieldError.getField();
            validationErrors.add(new ValidError(field, defaultMessage));
        }

        String errorMessage = objectMapper.writeValueAsString(validationErrors);

        ErrorResult errorResult = new ErrorResult(HttpStatus.BAD_REQUEST.name(),
                HttpStatus.BAD_REQUEST.value(), errorMessage);
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ValueInstantiationException.class})
    public ResponseEntity<ErrorResult> valueInstantiationExHandler(ValueInstantiationException e) {
        if (e.getCause() instanceof NullPointerException) {
            ErrorResult errorResult = new ErrorResult(
                    HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value()
                    , "?????? ?????? ???????????? ???????????????");
            return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
        }
        String message = e.getMessage();
        ErrorResult errorResult = new ErrorResult(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value(), message);
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorResult> contraintValidationExHandler(ConstraintViolationException e) {
        if (e.getCause() instanceof NullPointerException) {
            ErrorResult errorResult = new ErrorResult(
                    HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value()
                    , "?????? ?????? ???????????? ???????????????");
            return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
        }
        String message = e.getMessage();
        ErrorResult errorResult = new ErrorResult(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value(), message);
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ErrorResult> validationExHandler(ValidationException e) {
        if (e.getCause() instanceof NullPointerException) {
            ErrorResult errorResult = new ErrorResult(
                    HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value()
                    , "?????? ?????? ???????????? ???????????????");
            return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
        }
        String message = e.getMessage();
        ErrorResult errorResult = new ErrorResult(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value(), message);
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[exceptionHandler]", e);
        return new ErrorResult(HttpStatus.INTERNAL_SERVER_ERROR.name(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name());
    }

    @ExceptionHandler({BindException.class})
    public ResponseEntity<ErrorResult> bindExceptionHandler(BindException e) throws JsonProcessingException {
        log.error("[exceptionHandler]", e);
//        log.error("[exceptionHandler] [{}]",handlerMethod, e);
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        ValidationErrorMessage validationErrorMessage = new ValidationErrorMessage();
        List<ValidError> validationErrors = validationErrorMessage.getValidationErrors();

        for (FieldError fieldError : fieldErrors) {
            String defaultMessage = fieldError.getDefaultMessage();
            String field = fieldError.getField();
            validationErrors.add(new ValidError(field, defaultMessage));
        }

        String errorMessage = objectMapper.writeValueAsString(validationErrors);

        ErrorResult errorResult = new ErrorResult(HttpStatus.BAD_REQUEST.name(),
                HttpStatus.BAD_REQUEST.value(), errorMessage);
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<ErrorResult> notSupportMethodExHandler(Exception e) {
        log.error("[exceptionHandler]", e);
//        log.error("[exceptionHandler] [{}]",handlerMethod, e);
        ErrorResult errorResult = new ErrorResult(HttpStatus.METHOD_NOT_ALLOWED.name(),
                HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<ErrorResult> httpMessageExHandler(Exception e) {
        ErrorResult errorResult = new ErrorResult(HttpStatus.BAD_REQUEST.name(),
                HttpStatus.BAD_REQUEST.value(), "????????? ?????? ??? ?????????.");
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    //HttpRequestMethodNotSupportedException
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResult> httpMethodExHandler(Exception e) {
        ErrorResult errorResult = new ErrorResult(HttpStatus.NOT_FOUND.name(),
                HttpStatus.NOT_FOUND.value(), "???????????? ?????? ???????????????");
        return new ResponseEntity<>(errorResult, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public ResponseEntity<ErrorResult> maxUploadSizeExceededExHandler(MaxUploadSizeExceededException e) {
        ErrorResult errorResult = new ErrorResult(HttpStatus.BAD_REQUEST.name(),
                HttpStatus.BAD_REQUEST.value(), "????????? ????????? ???????????? ????????? ???????????????.");
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IllegalSortArgumentException.class})
    public ResponseEntity<ErrorResult> IllegalSortArgumentExHandler(IllegalSortArgumentException e) {

        ErrorResult errorResult = new ErrorResult(HttpStatus.BAD_REQUEST.name(),
                HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler({AsyncRequestTimeoutException.class})
//    public void AsyncRequestTimeoutExHandler(AsyncRequestTimeoutException e){
//        log.info("AsyncRequestTimeoutExHandler", e);
//    }
//
//    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
//    public void HttpMediaTypeNotAcceptableExHandler(HttpMediaTypeNotAcceptableException e){
//        log.info("HttpMediaTypeNotAcceptableExHandler", e);
//    }

//    @ExceptionHandler(value = AsyncRequestTimeoutException.class)
//    public String AsyncRequestTimeoutExHandler(AsyncRequestTimeoutException ex) {
//
//        log.info("SSE timeout");
//        return "SSE timeout... OK";
//    }
}

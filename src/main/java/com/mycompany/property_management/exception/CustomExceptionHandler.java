package com.mycompany.property_management.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorModel>> handleFieldValidation(MethodArgumentNotValidException manv){

        List<ErrorModel> errorModelList = new ArrayList<>();
        ErrorModel errorModel = null;
        List<FieldError> fieldErrorList = manv.getBindingResult().getFieldErrors();

        for(FieldError fe: fieldErrorList){
            errorModel = new ErrorModel();
            errorModel.setCode(fe.getField());
            errorModel.setMessage(fe.getDefaultMessage());
            errorModelList.add(errorModel);
        }
        return new ResponseEntity<List<ErrorModel>>(errorModelList, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<List<ErrorModel>> handleBusinessException(BusinessException businessException){
        for(ErrorModel err: businessException.getErrors()) {
            logger.debug("Business exception is thrown - level - debug: {} - {}", err.getCode(), err.getMessage());
            logger.info("Business exception is thrown - level - info: {} - {}", err.getCode(), err.getMessage());
            logger.warn("Business exception is thrown - level - warn: {} - {}", err.getCode(), err.getMessage());
            logger.error("Business exception is thrown - level - error: {} - {}", err.getCode(), err.getMessage());
        }
        return new ResponseEntity<List<ErrorModel>>(businessException.getErrors(), HttpStatus.BAD_REQUEST);
    }
}

package com.vuanhnguyenduc.aquariux.crypto.trading.exception;

import com.vuanhnguyenduc.aquariux.crypto.trading.constant.ErrorCodes;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private HttpStatus status;

    private String errorCode;

    private String errorMessage;

    public BaseException(ErrorCodes errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode.getErrorCode();
        this.errorMessage = errorCode.getErrorMessage();
        this.status = HttpStatus.BAD_REQUEST;
    }

    public BaseException(HttpStatus status, ErrorCodes errorCode) {
        super(errorCode.getErrorMessage());
        this.status = status;
        this.errorCode = errorCode.getErrorCode();
        this.errorMessage = errorCode.getErrorMessage();
    }

    public BaseException(ErrorCodes errorCode, String... args) {
        super(String.format(errorCode.getErrorMessage(), args));
        this.status = HttpStatus.BAD_REQUEST;
        this.errorCode = errorCode.getErrorCode();
        this.errorMessage = String.format(errorCode.getErrorMessage(), args);
    }
}

package com.vuanhnguyenduc.aquariux.crypto.trading.exception;

import com.vuanhnguyenduc.aquariux.crypto.trading.dto.ErrorResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<ErrorResponseDTO> handleBaseException(BaseException ex) {
        ErrorResponseDTO errorResponseDTO =
                ErrorResponseDTO.builder()
                        .errorCode(ex.getErrorCode())
                        .errorMessage(ex.getErrorMessage())
                        .build();
        return ResponseEntity.status(ex.getStatus()).body(errorResponseDTO);
    }
}

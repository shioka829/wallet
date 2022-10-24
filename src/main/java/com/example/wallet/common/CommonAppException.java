package com.example.wallet.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.http.HttpStatus;

import com.example.wallet.model.ComErrResponse;

@AllArgsConstructor
@Getter
public class CommonAppException extends RuntimeException {

    /** HTTPステータス */
    private HttpStatus httpStatus;
    /** エラーコード */
    private String errorCode;
    /** エラーメッセージ */
    private String errorMessage;

    public CommonAppException(HttpStatus httpStatus, ComErrResponse comErrResponse){
        this.httpStatus = httpStatus;
        this.errorCode = comErrResponse.getErrorCode();
        this.errorMessage = comErrResponse.getErrorMessage();
    }

}


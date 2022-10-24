package com.example.wallet.controller;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.wallet.common.ComErr;
import com.example.wallet.common.CommonAppException;
import com.example.wallet.model.ComErrResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 共通コントローラクラス
 * 業務例外（CommonAppException）およびその他の例外発生時にAPIレスポンスを作成するメソッドを提供する
 */
@RestController
@Slf4j
public class BaseController {
    /**
     * 業務例外（CommonAppException）を処理するクラス。
     * 業務例外（CommonAppException）からエラーコードを取得し、APIレスポンスに含め返却する。
     * @param e 業務例外（CommonAppException）
     * @return APIレスポンス
     */
    @ExceptionHandler(CommonAppException.class)
    public ResponseEntity<ComErrResponse> handleCommonAppException(CommonAppException e) {
        return ResponseEntity.status(e.getHttpStatus() == null ? HttpStatus.INTERNAL_SERVER_ERROR : e.getHttpStatus()).
        body(new ComErrResponse(e.getErrorCode(), e.getErrorMessage()));
    }

    /**
     * バリデーションエラー(ConstraintViolationException)を処理する
     * バリデーションエラーからエラーが発生している項目名を取得し、APIレスポンスに含め返却する。
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ComErrResponse handleConstraintViolationException(ConstraintViolationException e) {
       ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();        
       String name = violation.getPropertyPath().toString();
       int index = name.indexOf(".");
       name = name.substring(index + 1);
        return new ComErrResponse(ComErr.COM_VALUE_ERR, ComErr.COM_VALUE_ERR_MSG + name);
    }

    /**
     * その他の例外を処理するクラス。
     * エラーログ出力後、エラー時のAPIレスポンスを作成し返却する。
     * @param e 例外
     * @return APIレスポンス
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ComErrResponse handleException(Exception e) {
        log.error("Error occured.", e);
        return new ComErrResponse(ComErr.COM_SYSTEM_ERR, ComErr.COM_SYSTEM_ERR_MSG);
    }
}


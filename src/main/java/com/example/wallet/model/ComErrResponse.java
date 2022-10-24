package com.example.wallet.model;

import lombok.Data;

/**
 * 共通エラーレスポンスクラス
 */
@Data
public class ComErrResponse {
    /** エラーコード */
    public String errorCode;
    /** エラーメッセージ */
    public String errorMessage;

    public ComErrResponse(String errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}

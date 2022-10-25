package com.example.wallet.common;

public class ComErr {
    /*----------------------------------------*/
    /* 共通エラー
    /*----------------------------------------*/

    /**
     * 利用者情報登録
     * 入力値エラーです（型桁必須チェック不正）。不正項目：XXX, XXX, XXX
     */
    public static final String COM_VALUE_ERR = "CM000001";
    public static final String COM_VALUE_ERR_MSG = "入力値エラーです（型桁必須チェック不正）。不正項目：";

    /**
     * システムエラーが発生しました。
     */
    public static final String COM_SYSTEM_ERR = "CM999999";
    public static final String COM_SYSTEM_ERR_MSG = "システムエラーが発生しました。";

    /*----------------------------------------*/
    /* 新規登録エラー
    /*----------------------------------------*/
    public static final String USERID_DEPULICATION_ERR = "AE000001";
    public static final String USERID_DEPULICATION_ERR_MSG = "グループ名を入力してください。";

    /*----------------------------------------*/
    /* メンバー登録エラー
    /*----------------------------------------*/
    public static final String MEMBERNAME_DEPULICATION_ERR = "BE000001";
    public static final String MEMBERNAME_DEPULICATION_ERR_MSG = "メンバー名は既に使用されています。別のメンバー名を入力してください。";

    /*----------------------------------------*/
    /* 項目登録エラー
    /*----------------------------------------*/
    public static final String CATEGORYNAME_DEPULICATION_ERR = "CE000001";
    public static final String CATEGORYNAME_DEPULICATION_ERR_MSG = "項目名は既に使用されています。別の項目名を入力してください。";

    /*----------------------------------------*/
    /* 家計簿更新エラー
    /*----------------------------------------*/
    public static final String EXPENCE_UPDATE_ERR = "DE000001";
    public static final String EXPENCE_UPDATE_ERR_MSG = "更新対象の家計簿が見つかりませんでした。";

    /*----------------------------------------*/
    /* 家計簿削除エラー
    /*----------------------------------------*/
    public static final String EXPENCE_DELETE_ERR = "DE000002";
    public static final String EXPENCE_DELETE_ERR_MSG = "家計簿の削除に失敗しました。";
}

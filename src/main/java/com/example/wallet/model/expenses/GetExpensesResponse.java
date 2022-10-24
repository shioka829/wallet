package com.example.wallet.model.expenses;

import java.util.List;

import lombok.Data;

@Data
public class GetExpensesResponse {
    /** 家計簿リスト */
private List<Detail> expenses;
@Data
public class Detail{
    /** 家計簿ID */
    private int expensesId;
    /** 日付 */
    private String date;
    /** メンバー名 */
    private String memberName;
    /** カテゴリー名 */
    private String categoryName;
    /** 金額 */
    private int price;
    /** 内容 */
    private String detail;
}
}

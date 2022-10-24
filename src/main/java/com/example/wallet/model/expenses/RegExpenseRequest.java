package com.example.wallet.model.expenses;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RegExpenseRequest {
    @NotBlank
    @Size(max=36)
    private String groupid;
    @NotBlank(message = "日付を入力してください")
    @Size(max=8)
    private String date;
    @NotNull
    @Positive(message = "メンバーを選択してください")
    private int memberid;
    @NotNull
    @Positive(message = "カテゴリーを選択してください")
    private int categoryid;
    @NotNull
    @Positive(message = "金額を入力してください")
    private int price;
    @Size(max=256)
    private String detail;
}

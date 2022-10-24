package com.example.wallet.model.expenses;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DeleteExpenseRequest {
    @NotBlank
     /** グループID */
    private String groupid;
    @NotNull
    /** 家計簿ID */
    private int expensesid;
    @NotBlank
    /** 月 */
    private String month;
}

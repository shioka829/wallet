package com.example.wallet.model.expenses;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UpdExpenseRequest {
    @NotBlank
    @Size(max=36)
    private String groupid;
    @NotNull
    @PositiveOrZero
    private int expensesid;
    @NotBlank
    @Size(max=6)
    private String month;
    @NotBlank
    @Size(max=2)
    private String date;
    @NotNull
    @PositiveOrZero
    private int memberid;
    @NotNull
    @PositiveOrZero
    private int categoryid;
    @NotNull
    @PositiveOrZero
    private int price;
    @Size(max=256)
    private String detail;
    private boolean deleteflg;
    private boolean paidflg;
}

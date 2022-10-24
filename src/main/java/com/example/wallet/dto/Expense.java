package com.example.wallet.dto;

import lombok.Data;

@Data
public class Expense {
    private String groupId;
    private int expensesId;
    private String month;
    private String date;
    private int memberId;
    private int categoryId;
    private int price;
    private String detail;
    private boolean paidFlg;
    private boolean deleteFlg;
}
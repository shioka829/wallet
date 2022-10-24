package com.example.wallet.model.expenses;

import java.util.List;

import lombok.Data;

@Data
public class GetExpensesTotalResponse {
    /** メンバーごとの集計 */
    private List<MemberSubTotal> memberSubTotal;
    /** 項目ごとの集計 */
    private List<CategorySubTotal> categorySubTotal;
    /** 合計 */
    private int total;

    @Data
    public class MemberSubTotal{
        /** 名前 */
        private String name;
        /** 合計 */
        private int total;
    }

    @Data
    public class CategorySubTotal{
        /** 名前 */
        private String name;
        /** 合計 */
        private int total;
    }
}

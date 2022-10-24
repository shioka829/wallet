package com.example.wallet.model.group;

import lombok.Data;

@Data
public class SignupResponse {
    /** 結果 */
    private String result;
    /** グループID */
    private String groupId;
}

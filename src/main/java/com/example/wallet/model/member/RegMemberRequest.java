package com.example.wallet.model.member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RegMemberRequest {
    /** グループID */
    @NotBlank
    @Size(max=36)
    private String groupid;
    /** メンバー名 */
    @NotBlank(message = "メンバーの名前を入力してください")
    @Size(max=18,message = "メンバーの名前は18文字以内で入力してください")
    private String memberName;
}

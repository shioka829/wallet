package com.example.wallet.model.group;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignupRequest {
    @NotBlank(message = "グループ名が未入力です。")
    @Size(max=64, message = "グループ名は64文字以下で入力してください。")
    private String groupname;
}

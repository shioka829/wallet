package com.example.wallet.model.category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RegCategoryRequest {
    @NotBlank
    private String groupid;
    @NotBlank(message = "項目名を入力してください")
    @Size(max = 256, message = "項目名は256文字以内で入力してください")
    private String categoryName;
}

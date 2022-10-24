package com.example.wallet.model.category;

import java.util.List;

import com.example.wallet.dto.Category;

import lombok.Data;

@Data
public class GetCategoriesResponse {
    private List<Category> categories;
}

package com.example.wallet.controller;


import java.util.stream.Collectors;

import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.wallet.common.ComErr;
import com.example.wallet.common.CommonAppException;
import com.example.wallet.model.category.GetCategoriesResponse;
import com.example.wallet.model.category.RegCategoryRequest;
import com.example.wallet.model.category.RegCategoryResponse;
import com.example.wallet.service.CategoryService;

@RestController
@Validated
@RequestMapping(path = "/api")
public class CategoryController extends BaseController {
    
    @Autowired
    CategoryService service;

    /**
     * 項目の一覧を取得する
     * @param request
     * @param bindingResult
     * @return
     */
    @GetMapping(path = "/categories")
    @CrossOrigin
    public GetCategoriesResponse getCategories(@RequestParam(name = "groupid", required = true) @Validated @Size(max=26) String groupId) {
        GetCategoriesResponse response = service.getCategories(groupId);
        return response;
    }

    /**
     * 項目を追加する
     * @param request
     * @param bindingResult
     * @return
     */
    @PostMapping(path = "/category")
    @CrossOrigin
    public RegCategoryResponse regCategory(@RequestBody @Validated RegCategoryRequest request, BindingResult bindingResult){
        // 引数のバリデーションチェック
        if (bindingResult.hasErrors()) {
            // レスポンスの返却
            throw new CommonAppException(HttpStatus.BAD_REQUEST, ComErr.COM_VALUE_ERR, bindingResult.getFieldError().getDefaultMessage());
        }

        RegCategoryResponse response = service.regCategory(request);

        return response;
    }
}

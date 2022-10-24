package com.example.wallet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.wallet.common.ComErr;
import com.example.wallet.common.CommonAppException;
import com.example.wallet.dto.Category;
import com.example.wallet.mapper.CategoryMapper;
import com.example.wallet.model.category.GetCategoriesResponse;
import com.example.wallet.model.category.RegCategoryRequest;
import com.example.wallet.model.category.RegCategoryResponse;

@Service
@Transactional
public class CategoryService {
    @Autowired
    CategoryMapper mapper;

    /**
     * 項目一覧を取得
     * @param request
     * @return
     */
    public GetCategoriesResponse getCategories(String groupId){

        // 項目の一覧を取得
        List<Category>categories = mapper.selectCategories(groupId);

        GetCategoriesResponse response = new GetCategoriesResponse();
        response.setCategories(categories);

        return response;
    }

    /**
     * 項目を追加
     * @param request
     * @return
     */
    public RegCategoryResponse regCategory(RegCategoryRequest request){

        RegCategoryResponse response = new RegCategoryResponse();

        int categoryIdMax = 0;

        // 項目の一覧を取得
        List<Category>categories = mapper.selectCategories(request.getGroupid());

        if (0 < categories.size()){
            for (Category category : categories){
                // 登録したい項目の項目名が既存の項目名と重複した場合、エラーで終了
                if (request.getCategoryName().equals(category.getCategoryName())){
                    throw new CommonAppException(HttpStatus.BAD_REQUEST, ComErr.CATEGORYNAME_DEPULICATION_ERR,
                        ComErr.CATEGORYNAME_DEPULICATION_ERR_MSG);
                }
            }
            // 現在のcategoryIdの最大値を設定し、categoryIdMaxに設定する
            categoryIdMax = mapper.selectCategoryIdMax(request.getGroupid());
        }

        // DTOに値を設定
        Category category = new Category();
        category.setGroupId(request.getGroupid());
        category.setCategoryId(categoryIdMax + 1);
        category.setCategoryName(request.getCategoryName());

        // insert
        boolean result = mapper.insertCategory(category);

        if (!result){
            response.setResult("NG");
            return response;
        }

        response.setResult("OK");
        return response;
    }
}

package com.example.wallet.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.wallet.dto.Category;

@Mapper
public interface CategoryMapper {

    /**
     * 項目の一覧を取得
     * @param groupId
     * @return
     */
    @Select({ "SELECT * FROM category WHERE group_id = #{groupId}" })
    List<Category> selectCategories(String groupId);

    /**
     * 項目を登録
     * @param category
     * @return
     */
    @Insert({ "INSERT INTO category (group_id, category_id, category_name) VALUES(#{groupId}, #{categoryId}, #{categoryName})" })
    boolean insertCategory(Category category);

    /**
     * 項目IDの最大値を取得
     * @param groupId
     * @return
     */
    @Select({ "SELECT MAX(category_id) FROM category WHERE group_id = #{groupId}"})
    int selectCategoryIdMax(String groupId);

    /**
     * 項目を取得
     * @param groupId
     * @return
     */
    @Select({ "SELECT * FROM category WHERE group_id = #{groupId} and category_id = #{categoryId}" })
    Category selectCategory(String groupId, int categoryId);

}

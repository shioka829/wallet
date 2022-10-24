package com.example.wallet.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.wallet.dto.Expense;

@Mapper
public interface ExpensesMapper {

    /**
     * 家計簿情報を取得する(月で全部、削除フラグがオフ)
     * @param groupId
     * @param month
     * @return
     */
    @Select({ "SELECT * FROM expenses WHERE group_id = #{groupId} and month = #{month} and delete_flg = false ORDER BY date" })
    List<Expense> selectExpenses(String groupId, String month);

        /**
     * 家計簿情報を取得する(月で全部)
     * @param groupId
     * @param month
     * @return
     */
    @Select({ "SELECT * FROM expenses WHERE group_id = #{groupId} and month = #{month} ORDER BY date" })
    List<Expense> selectExpensesAll(String groupId, String month);

    /**
     * 家計簿情報を取得する(ID指定)
     * @param groupId
     * @param month
     * @return
     */
    @Select({ "SELECT * FROM expenses WHERE group_id = #{groupId} and month = #{month} and expenses_id = #{expensesId} and delete_flg = false" })
    Expense selectExpense(String groupId, String month, int expensesId);

    /**
     * 月ごとの金額合計を取得する
     * @param groupId
     * @param month
     * @return
     */
    @Select({ "SELECT  COALESCE(SUM(price), 0) FROM expenses WHERE group_id = #{groupId} and month = #{month} and delete_flg = false" })
    int selectExpenseTotal(String groupId, String month);

    /**
     * 月ごとの金額合計を取得する(メンバーID指定)
     * @param groupId
     * @param month
     * @param memberId
     * @return
     */
    @Select({ "SELECT  COALESCE(SUM(price), 0) FROM expenses WHERE group_id = #{groupId} and month = #{month} and member_id = #{memberId} and delete_flg = false"})
    int selectTotalMember(String groupId, String month, int memberId);

    /**
     * 月ごとの金額合計を取得する(項目ID指定)
     * @param groupId
     * @param month
     * @param categoryId
     * @return
     */
    @Select({ "SELECT  COALESCE(SUM(price), 0) FROM expenses WHERE group_id = #{groupId} and month = #{month} and category_id = #{categoryId} and delete_flg = false" })
    int selectTotalCategory(String groupId, String month, int categoryId);

    /**
     * 家計簿情報を追加する
     * @param expense
     * @return
     */
    @Insert({ "INSERT INTO expenses (group_id, expenses_id, member_id, month, date, category_id, price, detail) " +
    "VALUES(#{groupId}, #{expensesId}, #{memberId}, #{month}, #{date}, #{categoryId}, #{price}, #{detail})"})
    boolean insertExpense(Expense expense);

    /**
     * 家計簿IDのMAX値を取得する
     * @param groupId
     * @return
     */
    @Select({ "SELECT MAX(expenses_id) FROM expenses WHERE group_id = #{groupId}"})
    int selectexpensesIdMax(String groupId);

    /**
     * 家計簿情報を更新する
     * @param expense
     * @return
     */
    @Insert({ 
        "UPDATE expenses SET",
        " member_id = #{memberId}",
        ", date = #{date}",
        ", category_id = #{categoryId}",
        ", price = #{price}",
        ", detail = #{detail}",
        ", paid_flg = #{paidFlg}",
        ", delete_flg = #{deleteFlg}",
        " WHERE",
        " group_id = #{groupId}",
        " and expenses_id = #{expensesId}",
        " and month = #{month}",
    })
    boolean updateExpense(Expense expense);

    @Update({ "UPDATE expenses SET delete_flg = true WHERE group_id = #{groupId} and expenses_id = #{expensesId} and month = #{month}"})
    boolean deleteExpense(String groupId, int expensesId, String month);
}

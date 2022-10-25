package com.example.wallet.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.wallet.common.ComErr;
import com.example.wallet.common.CommonAppException;
import com.example.wallet.dto.Category;
import com.example.wallet.dto.Expense;
import com.example.wallet.dto.Member;
import com.example.wallet.mapper.CategoryMapper;
import com.example.wallet.mapper.ExpensesMapper;
import com.example.wallet.mapper.MemberMapper;
import com.example.wallet.model.expenses.DeleteExpenseRequest;
import com.example.wallet.model.expenses.DeleteExpenseRespose;
import com.example.wallet.model.expenses.GetExpensesResponse;
import com.example.wallet.model.expenses.GetExpensesTotalResponse;
import com.example.wallet.model.expenses.RegExpenseRequest;
import com.example.wallet.model.expenses.RegExpenseResponse;
import com.example.wallet.model.expenses.UpdExpenseRequest;
import com.example.wallet.model.expenses.UpdExpenseResponse;
import com.example.wallet.model.expenses.GetExpensesResponse.Detail;
import com.example.wallet.model.expenses.GetExpensesTotalResponse.CategorySubTotal;
import com.example.wallet.model.expenses.GetExpensesTotalResponse.MemberSubTotal;

@Service
@Transactional
public class ExpensesService {

    @Autowired
    ExpensesMapper mapper;

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    CategoryMapper categoryMapper;

    /**
     * 家計簿情報の一覧を取得
     * 
     * @param request
     * @return
     */
    public GetExpensesResponse getExpenses(String groupId, String month){
        GetExpensesResponse response = new GetExpensesResponse();

        List<Expense> list = mapper.selectExpenses(groupId, month);

        List<Detail> detailList = new ArrayList<>();
        for(Expense expense : list){
            // メンバー名を検索する
            Member member = memberMapper.selectMember(expense.getGroupId(), expense.getMemberId());
            // 項目名を検索する
            Category category = categoryMapper.selectCategory(expense.getGroupId(), expense.getCategoryId());

            // 項目名を検索する
            GetExpensesResponse.Detail detail = (new GetExpensesResponse()).new Detail();
            detail.setExpensesId(expense.getExpensesId());

            detail.setDate(expense.getDate());
            detail.setMemberName(member.getMemberName());
            detail.setCategoryName(category.getCategoryName());
            detail.setPrice(expense.getPrice());
            detail.setDetail(expense.getDetail());
            detailList.add(detail);
        }

        response.setExpenses(detailList);

        return response;
    }

    /**
     * 家計簿情報の登録
     * 
     * @param request
     * @return
     */
    public RegExpenseResponse regExpense(RegExpenseRequest request){
        RegExpenseResponse response = new RegExpenseResponse();
        Expense expense = new Expense();

        int expenseMax = 0;
        String month = request.getDate().substring(0,6);

        // 詳細を取得
        List<Expense> list = mapper.selectExpensesAll(request.getGroupid(), month);

        if (0 < list.size()){
            expenseMax = mapper.selectexpensesIdMax(request.getGroupid());
        }

        // DTOに設定
        expense.setGroupId(request.getGroupid());
        expense.setExpensesId(expenseMax + 1);
        expense.setMonth(month);
        expense.setDate(request.getDate().substring(6,8));
        expense.setMemberId(request.getMemberid());
        expense.setCategoryId(request.getCategoryid());
        expense.setPrice(request.getPrice());
        expense.setDetail(request.getDetail());
        // insert
        boolean result = mapper.insertExpense(expense);

        if (!result){
            response.setResult("NG");
        }

        response.setResult("OK");

        return response;
    }

    /**
     * 家計簿情報を更新
     * @param request
     * @return
     */
    public UpdExpenseResponse updExpense(UpdExpenseRequest request){
        UpdExpenseResponse response = new UpdExpenseResponse();

        // 更新対象の詳細を取得
        Expense expense = mapper.selectExpense(request.getGroupid(), request.getMonth(), request.getExpensesid());

        if (expense == null){
            throw new CommonAppException(HttpStatus.BAD_REQUEST, ComErr.EXPENCE_UPDATE_ERR,
            ComErr.EXPENCE_UPDATE_ERR_MSG);
        }

        expense.setCategoryId(request.getCategoryid());
        expense.setDate(request.getDate());
        expense.setDeleteFlg(request.isDeleteflg());
        expense.setDetail(request.getDetail());
        expense.setMemberId(request.getMemberid());
        expense.setPrice(request.getPrice());
        expense.setPaidFlg(request.isPaidflg());

        boolean result = mapper.updateExpense(expense);

        if (!result){
            response.setResult("NG");
        }

        response.setResult("OK");

        return response;
    }

    /**
     * 家計簿の合計を取得する
     * @param request
     * @return
     */
    public GetExpensesTotalResponse getExpensesTotal(String groupId, String month){
        // 返却値
        GetExpensesTotalResponse response = new GetExpensesTotalResponse();

        // 月ごとの金額合計を取得する
        int total = mapper.selectExpenseTotal(groupId, month);
        response.setTotal(total);

        // メンバーの一覧を取得する
        List<Member>members = memberMapper.selectMembers(groupId);
        // メンバーごとの家計簿合計を入れるためのリスト
        List<MemberSubTotal>mSubtotal = new ArrayList<>();

        // メンバーの人数分以下の処理を繰り返す
        for(Member member: members){
            // メンバーID指定で金額の合計を取得する 
            int subtotal = mapper.selectTotalMember(groupId, month, member.getMemberId());
            // 返却値(Member)
            GetExpensesTotalResponse.MemberSubTotal memberRes = (new GetExpensesTotalResponse()).new MemberSubTotal();
            // 返却値(Member)のメンバー名に名前を設定する
            memberRes.setName(member.getMemberName());
            // 返却値(Member)の合計に算出した合計を設定する
            memberRes.setTotal(subtotal);
            // リストに追加
            mSubtotal.add(memberRes);
        }

        // responseに設定する
        response.setMemberSubTotal(mSubtotal);

        // 項目の一覧を取得する
        List<Category>categories = categoryMapper.selectCategories(groupId);
        // 項目ごとの家計簿合計を入れるためのリスト
        List<CategorySubTotal>cSubtotal = new ArrayList<>();

        // 項目の数分以下の処理を繰り返す
        for(Category category: categories){
            // 項目ID指定で金額の合計を取得する
            int subtotal = mapper.selectTotalCategory(groupId, month, category.getCategoryId());
            // 返却値(Category)
            GetExpensesTotalResponse.CategorySubTotal categoryRes = (new GetExpensesTotalResponse()).new CategorySubTotal();
            // 返却値(Category)の項目名に名前を設定する
            categoryRes.setName(category.getCategoryName());
            // 返却値(Category)の合計に算出した合計を設定する
            categoryRes.setTotal(subtotal);
            // リストに追加
            cSubtotal.add(categoryRes);
        }

        // responseに設定する
        response.setCategorySubTotal(cSubtotal);

        return response;
    }

    /**
     * 家計簿を削除する
     * @param request
     * @return
     */
    public DeleteExpenseRespose deleteExpense(DeleteExpenseRequest request){

        DeleteExpenseRespose response = new DeleteExpenseRespose();

        // 更新対象の詳細を取得
        Expense expense = mapper.selectExpense(request.getGroupid(), request.getMonth(), request.getExpensesid());

        if (expense == null){
            throw new CommonAppException(HttpStatus.BAD_REQUEST, ComErr.EXPENCE_DELETE_ERR,
            ComErr.EXPENCE_DELETE_ERR_MSG);
        }

        boolean result = mapper.deleteExpense(request.getGroupid(), request.getExpensesid(), request.getMonth());

        if (!result){
            response.setResult("NG");
        }

        response.setResult("OK");

        return response;

    }
}

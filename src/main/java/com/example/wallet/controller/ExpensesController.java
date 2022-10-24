package com.example.wallet.controller;

import java.util.stream.Collectors;

import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.wallet.common.ComErr;
import com.example.wallet.common.CommonAppException;
import com.example.wallet.model.expenses.DeleteExpenseRequest;
import com.example.wallet.model.expenses.DeleteExpenseRespose;
import com.example.wallet.model.expenses.GetExpensesResponse;
import com.example.wallet.model.expenses.GetExpensesTotalResponse;
import com.example.wallet.model.expenses.RegExpenseRequest;
import com.example.wallet.model.expenses.RegExpenseResponse;
import com.example.wallet.model.expenses.UpdExpenseRequest;
import com.example.wallet.model.expenses.UpdExpenseResponse;
import com.example.wallet.service.ExpensesService;

@RestController
@Validated
@RequestMapping(path = "/api")
public class ExpensesController extends BaseController{
    @Autowired
    ExpensesService service;

    /**
     * 家計簿情報一覧の取得
     * @param request
     * @param bindingResult
     * @return
     */
    @GetMapping(path = "/expenses")
    @CrossOrigin
    public GetExpensesResponse getExpenses(@RequestParam(name = "groupid", required = true) @Validated @Size(max=26) String groupId,
                                           @RequestParam(name = "month", required = true) @Validated @Size(min=5,max=6) String month) {
        GetExpensesResponse response = service.getExpenses(groupId, month);
        return response;
    }

    /**
     * 家計簿情報登録
     * @param request
     * @param bindingResult
     * @return
     */
    @PostMapping(path = "/expense")
    @CrossOrigin
    public RegExpenseResponse regExpenses(@RequestBody @Validated RegExpenseRequest request, BindingResult bindingResult){
        // 引数のバリデーションチェック
        if (bindingResult.hasErrors()) {
            // レスポンスの返却
            throw new CommonAppException(HttpStatus.BAD_REQUEST, ComErr.COM_VALUE_ERR, bindingResult.getFieldError().getDefaultMessage());
        }

        RegExpenseResponse response = service.regExpense(request);
        return response;
    }

    /**
     * 家計簿情報更新
     * @param request
     * @param bindingResult
     * @return
     */
    @PutMapping(path = "/expense")
    @CrossOrigin
    public UpdExpenseResponse updExpenses(@RequestBody @Validated UpdExpenseRequest request, BindingResult bindingResult){
        // 引数のバリデーションチェック
        if (bindingResult.hasErrors()) {
            String errParams = bindingResult.getFieldErrors().stream().map(FieldError::getField)
                    .collect(Collectors.joining(", "));
            // レスポンスの返却
            throw new CommonAppException(HttpStatus.BAD_REQUEST, ComErr.COM_VALUE_ERR,
                    ComErr.COM_VALUE_ERR_MSG + errParams);
        }

        UpdExpenseResponse response = service.updExpense(request);
        return response;
    } 

    /**
     * 家計簿の小計・合計を取得
     * @param groupId
     * @param month
     * @return
     */
    @GetMapping(path = "/expenses-total")
    @CrossOrigin
    public GetExpensesTotalResponse getExpensesTotal(@RequestParam(name = "groupid", required = true) @Validated @Size(max=26) String groupId,
                                                     @RequestParam(name = "month", required = true) @Validated @Size(min=5,max=6) String month) {
                                                        
        GetExpensesTotalResponse response = service.getExpensesTotal(groupId, month);
        return response;
    }

    /**
     * 家計簿詳細を削除
     * @param request
     * @param bindingResult
     * @return
     */
    @DeleteMapping(path = "/expense")
    public DeleteExpenseRespose deleteExpense(@RequestBody @Validated DeleteExpenseRequest request, BindingResult bindingResult){
        // 引数のバリデーションチェック
        if (bindingResult.hasErrors()) {
            String errParams = bindingResult.getFieldErrors().stream().map(FieldError::getField)
                    .collect(Collectors.joining(", "));
            // レスポンスの返却
            throw new CommonAppException(HttpStatus.BAD_REQUEST, ComErr.COM_VALUE_ERR,
                    ComErr.COM_VALUE_ERR_MSG + errParams);
        }

        DeleteExpenseRespose response = service.deleteExpense(request);
        return response;
    }
}

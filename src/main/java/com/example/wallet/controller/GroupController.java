package com.example.wallet.controller;

import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
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
import com.example.wallet.model.group.GetGroupResponse;
import com.example.wallet.model.group.SignupRequest;
import com.example.wallet.model.group.SignupResponse;
import com.example.wallet.service.GroupService;

@RestController
@RequestMapping(path = "/api")
public class GroupController extends BaseController{

    @Autowired
    GroupService service;

    @PostMapping(path = "/signup")
    @CrossOrigin
    public SignupResponse signup(@RequestBody @Validated SignupRequest request, BindingResult bindingResult) {

        // 引数のバリデーションチェック
        if (bindingResult.hasErrors()) {
            // レスポンスの返却
            throw new CommonAppException(HttpStatus.BAD_REQUEST, ComErr.COM_VALUE_ERR, bindingResult.getFieldError().getDefaultMessage());
        }
        SignupResponse response = service.signup(request);

        return response;
    }

    /**
     * グループ情報を取得する
     * @param groupId
     * @return
     */
    @GetMapping(path = "group")
    @CrossOrigin
    public GetGroupResponse getGroup(@RequestParam(name = "groupid", required = true) @Validated @Size(max=64) String groupId){

        GetGroupResponse response = service.getGroup(groupId);
        return response;
    }
}

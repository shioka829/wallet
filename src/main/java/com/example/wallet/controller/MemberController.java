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
import com.example.wallet.model.member.GetMembersResponse;
import com.example.wallet.model.member.RegMemberRequest;
import com.example.wallet.model.member.RegMemberResponse;
import com.example.wallet.service.MemberService;

@RestController
@RequestMapping(path = "/api")
public class MemberController extends BaseController {
    @Autowired
    MemberService service;

    /**
     * メンバーの一覧を取得
     * @param request
     * @param bindingResult
     * @return
     */
    @GetMapping(path = "/members")
    @CrossOrigin
    public GetMembersResponse getMembers(@RequestParam(name = "groupid", required = true) @Validated @Size(max=26) String groupId) {
        
        GetMembersResponse response = service.getMembers(groupId);

        return response;
    }

    /**
     * メンバーを追加する
     * @param request
     * @param bindingResult
     * @return
     */
    @PostMapping(path = "/member")
    @CrossOrigin
    public RegMemberResponse regMember(@RequestBody @Validated RegMemberRequest request, BindingResult bindingResult) {
        
        // 引数のバリデーションチェック
        if (bindingResult.hasErrors()) {
            // レスポンスの返却
            throw new CommonAppException(HttpStatus.BAD_REQUEST, ComErr.COM_VALUE_ERR, bindingResult.getFieldError().getDefaultMessage());
        }

        RegMemberResponse response = service.regMember(request);

        return response;
    }
}

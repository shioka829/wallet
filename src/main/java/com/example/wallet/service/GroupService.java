package com.example.wallet.service;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.wallet.common.ComErr;
import com.example.wallet.common.CommonAppException;
import com.example.wallet.dto.Group;
import com.example.wallet.mapper.GroupMapper;
import com.example.wallet.model.group.GetGroupResponse;
import com.example.wallet.model.group.SignupRequest;
import com.example.wallet.model.group.SignupResponse;

@Service
@Transactional
public class GroupService {

    @Autowired
    GroupMapper mapper;
    
    /**
     * ユーザー登録
     * @param request
     * @return
     */
    public SignupResponse signup(SignupRequest request){
        SignupResponse response = new SignupResponse();

        // グループIDの生成
        String groupId = this.makeGroupId(26);

       // DTOに詰め替え
       Group group =new Group();
       group.setGroupId(groupId);
       group.setGroupName(request.getGroupname());

        // グループテーブルにinsert
        boolean result = mapper.insertGroup(group);

        if(!result){
            response.setResult("NG");
        }

        response.setResult("OK");
        response.setGroupId(groupId);

        return response;
    }

    /**
     * グループを取得する
     * @param groupId
     * @return
     */
    public GetGroupResponse getGroup(String groupId){
        Group group = mapper.selectGroup(groupId);
        if(group == null){
            throw new CommonAppException(HttpStatus.BAD_REQUEST, ComErr.COM_VALUE_ERR, "グループが見つかりませんでした。");
        }

        GetGroupResponse response = new GetGroupResponse();
        response.setGroupName(group.getGroupName());

        return response;
    }

    /**
     * グループID生成ロジック
     * @param len
     * @return
     */
    public String makeGroupId(int len)
    {
        // ASCII範囲–英数字(0-9、a-z、A-Z)
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
 
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
 
        for (int i = 0; i < len; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
 
        return sb.toString();
    }
}

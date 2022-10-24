package com.example.wallet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.wallet.common.ComErr;
import com.example.wallet.common.CommonAppException;
import com.example.wallet.dto.Member;
import com.example.wallet.mapper.MemberMapper;
import com.example.wallet.model.member.GetMembersResponse;
import com.example.wallet.model.member.RegMemberRequest;
import com.example.wallet.model.member.RegMemberResponse;

@Service
@Transactional
public class MemberService {

    @Autowired
    MemberMapper mapper;
    /**
     * メンバー一覧を取得する
     * @param request
     * @return
     */
    public GetMembersResponse getMembers(String groupId){
        GetMembersResponse response = new GetMembersResponse();
        List<Member> members = mapper.selectMembers(groupId);
        // if (members.size() == 0){
        //     throw new CommonAppException(HttpStatus.BAD_REQUEST, ComErr.MEMBER_NOT_FOUND_ERR,
        //     ComErr.MEMBER_NOT_FOUND_ERR_MSG);
        // }
        response.setMembers(members);
        return response;
    }

    /**
     * メンバーを登録する
     * @param request
     * @return
     */
    public RegMemberResponse regMember(RegMemberRequest request){
        RegMemberResponse response = new RegMemberResponse();

        int memberIdMax = 0;

        // メンバーの一覧を取得する
        List<Member>members = mapper.selectMembers(request.getGroupid());

        if (0 < members.size()){
            for (Member member : members){
                // 登録したいメンバーのメンバー名が既存のメンバー名と重複した場合、エラーで終了
                if (request.getMemberName().equals(member.getMemberName())){
                    throw new CommonAppException(HttpStatus.BAD_REQUEST, ComErr.MEMBERNAME_DEPULICATION_ERR,
                        ComErr.MEMBERNAME_DEPULICATION_ERR_MSG);
                }
            }
            // 現在のmemberIdの最大値を設定し、memberIdMaxに設定する
            memberIdMax = mapper.selectMemberIdMax(request.getGroupid());
        }

        // DTOに詰め替え
        Member member = new Member();
        member.setGroupId(request.getGroupid());
        member.setMemberId(memberIdMax + 1);
        member.setMemberName(request.getMemberName());

        // insert
        boolean result = mapper.insertMember(member);

        if (!result){
            response.setResult("NG");
        }

        response.setResult("OK");
        return response;
    }
}

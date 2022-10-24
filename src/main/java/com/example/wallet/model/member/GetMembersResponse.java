package com.example.wallet.model.member;

import java.util.List;

import com.example.wallet.dto.Member;

import lombok.Data;

@Data
public class GetMembersResponse {
    /** メンバーリスト */
    private List<Member> members;
}

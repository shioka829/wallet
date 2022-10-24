package com.example.wallet.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.wallet.dto.Member;

@Mapper
public interface MemberMapper {

    /**
     * メンバー一覧を取得する
     * @param userId
     * @return
     */
    @Select({ "SELECT * FROM members WHERE group_id = #{groupId}" })
    List<Member> selectMembers(String groupId);

    /**
     * メンバーを取得する
     * @param userId
     * @return
     */
    @Select({ "SELECT * FROM members WHERE group_id = #{groupId} and member_id = #{memberId}" })
    Member selectMember(String groupId, int memberId);

    /**
     * メンバーを登録する
     * @param member
     * @return
     */
    @Insert({ "INSERT INTO members (group_id, member_id, member_name) VALUES(#{groupId}, #{memberId}, #{memberName})"})
    boolean insertMember(Member member);

    /**
     * メンバーIDの最大値を持つメンバーを取得
     * @param groupId
     * @return
     */
    @Select({ "SELECT MAX(member_id) FROM members WHERE group_id = #{groupId}"})
    int selectMemberIdMax(String groupId);
}

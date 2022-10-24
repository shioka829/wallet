package com.example.wallet.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.wallet.dto.Group;

@Mapper
public interface GroupMapper {

    /**
     * グループにinsertする
     * @param group
     * @return
     */
    @Insert({ "INSERT INTO groups (group_id, group_name) VALUES(#{groupId}, #{groupName})"})
    boolean insertGroup(Group group);

    /**
     * グループ情報を取得する
     * @param groupId
     * @return
     */
    @Select({ "SELECT * FROM groups WHERE group_id = #{groupId}" })
    Group selectGroup(String groupId);
}

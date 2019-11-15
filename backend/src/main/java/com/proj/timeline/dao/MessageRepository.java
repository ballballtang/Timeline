package com.proj.timeline.dao;

import com.proj.timeline.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author WEI Tang
 * @Date 2019-11-14
 * Message Repository
 */

@Repository
public interface MessageRepository extends JpaRepository<Message,Integer> {
    //获取消息数量
    @Query(value = "select count(* )from message ",nativeQuery = true)
    int getNum();

    //获取
    @Query(value = "select max(ID )from message ",nativeQuery = true)
    int getMaxId();

    //通过ID查找消息
    @Query(value = "select * from message where id < :id order by id desc limit 1",nativeQuery = true)
    Message selectMsgById(@Param("id") int id);

    //获取最新的num条消息
    @Query(value = "select * from message order by id desc limit :num",nativeQuery = true)
    List<Message> selectUpdateMessage(@Param("num") int num);


}

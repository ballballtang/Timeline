package com.proj.timeline.controller;

import com.proj.timeline.dao.MessageRepository;
import com.proj.timeline.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * @Author WEI Tang
 * @Date 2019-11-14
 * Message Controller
 */
@Controller
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    //获取最新三条message
    @RequestMapping(value = "/getMessage",method = RequestMethod.POST)
    @ResponseBody
    List<Message> getMessage() {
        List<Message> messagesList = messageRepository.selectUpdateMessage(3);
        return messagesList;
    }

    //获取最新num条message
    @RequestMapping(value = "/getUpdateMessage",method = RequestMethod.POST)
    @ResponseBody
    List<Message> getUpdateMessage(@RequestBody Message m) {
        List<Message> messagesList = messageRepository.selectUpdateMessage(m.getId());
        return messagesList;
    }

    //获取第一条小于ID值的message
    @RequestMapping(value = "/getNewMessage",method = RequestMethod.POST)
    @ResponseBody
    Message getNewMessage(@RequestBody Message m) {
        Message msgList = messageRepository.selectMsgById(m.getId());

        return msgList;
    }

    //获取message数量
    @RequestMapping(value = "/getNum", method = RequestMethod.POST)
    @ResponseBody
    int getNum() {
        return messageRepository.getNum();
    }

}

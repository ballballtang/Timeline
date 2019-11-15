package com.proj.timeline.controller;


import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.proj.timeline.dao.MessageRepository;
import com.proj.timeline.entity.Message;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
class MessageControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    MessageRepository messageRepository;
    MessageController messageController;

    @Test
    void testGetMessage() throws Exception{
        ResultActions perform = mockMvc.perform(post("/getMessage"));
        perform.andExpect(status().isOk());
        verify(messageRepository).selectUpdateMessage(3);
        MvcResult result = perform.andReturn();
        System.out.println(result.getResponse().getContentLength());
    }
    @Test
    void testGetUpdateMessage() throws  Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",5);
        String requestJson = JSONObject.toJSONString(jsonObject);
        ResultActions perform = mockMvc.perform(post("/getUpdateMessage").contentType(MediaType.APPLICATION_JSON).content(requestJson));
        perform.andExpect(status().isOk());

        verify(messageRepository).selectUpdateMessage(5);
    }
    @Test
    void testGetNewMessage() throws  Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",4);
        String requestJson = JSONObject.toJSONString(jsonObject);
        Message msg = new Message();
        msg.setId(4);
        when(messageRepository.selectMsgById(anyInt())).thenReturn(msg);

        ResultActions perform = mockMvc.perform(post("/getNewMessage").contentType(MediaType.APPLICATION_JSON).content(requestJson));
        perform.andExpect(status().isOk());

        verify(messageRepository).selectMsgById(4);
        System.out.println(perform.andReturn().getResponse().getContentAsString());
    }
    @Test
    void testGetNum() throws Exception{
        ResultActions perform = mockMvc.perform(post("/getNum"));
        perform.andExpect(status().isOk());
        when(messageRepository.getNum()).thenReturn(4);
        verify(messageRepository).getNum();
        System.out.println(messageRepository.getNum());
    }
}
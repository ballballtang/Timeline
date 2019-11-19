package com.proj.timeline.dao;


import com.proj.timeline.entity.Message;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext
public class MessageRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private MessageRepository repo;

    private Message new_feature_message1;
    private Message new_feature_message2;

    @Before
    public void init() {
        new_feature_message1 = new Message.MessageBuilder()
                .author("TangWei")
                .content("Junit is interesting.")
                .img("D://test")
                .build();
        new_feature_message2 = new Message.MessageBuilder()
                .author("BBT")
                .content("aaabbbbb")
                .img("D://test")
                .build();
    }

    @After
    public void cleanup() {
        this.entityManager.clear();
    }

    @Test
    public void testGetNum(){
        this.entityManager.persist(new_feature_message1);
        this.entityManager.persist(new_feature_message2);

        assertEquals(2,repo.getNum());

    }

    @Test
    public void testSelectMsgById(){
        this.entityManager.persist(new_feature_message1);
        this.entityManager.persist(new_feature_message2);
        Message msg = repo.selectMsgById(2);

        assertEquals("TangWei",msg.getAuthor());
        assertEquals("Junit is interesting.",msg.getContent());
        assertEquals("D://test",msg.getImg());
    }

    @Test
    public void testSelectUpdateMessage(){
        this.entityManager.persist(new_feature_message1);
        this.entityManager.persist(new_feature_message2);
        List<Message> messages = repo.selectUpdateMessage(2);
        int count = messages.size();

        assertEquals(2,messages.get(0).getId());
        assertEquals("BBT",messages.get(0).getAuthor());
        assertEquals("aaabbbbb",messages.get(0).getContent());
        assertEquals("D://test",messages.get(0).getImg());

        assertEquals(1,messages.get(1).getId());
        assertEquals("TangWei",messages.get(1).getAuthor());
        assertEquals("Junit is interesting.",messages.get(1).getContent());
        assertEquals("D://test",messages.get(1).getImg());

        assertEquals(2, count);
    }
}
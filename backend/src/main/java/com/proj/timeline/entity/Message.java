package com.proj.timeline.entity;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @Author WEI Tang
 * @Date 2019-11-11
 * Message Entity
 */

@Entity
@Table(name = "message")
public class Message {
    //消息ID，主键，自动增加
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    int id;

    //消息作者名
    @Column(name = "author")
    String author;

    //消息内容
    @Column(name = "content")
    String content;

    //消息图片路径
    @Column(name = "img")
    String img;

    //时间戳
    @Column(name = "timestamp")
    Timestamp timestamp;

    public Message(){

    }
    public Message(String author, String content, String img){
        this.author = author;
        this.content = content;
        this.img = img;
    }

    public  int getId() { return id;}

    public String getAuthor() { return author;}

    public String getContent() { return content; }

    public String getImg() { return img;}

    public Timestamp getTimestamp() { return timestamp;}

    public void setId(int i) { id = i;}

    public void setAuthor(String a) { author = a;}

    public void setContent(String c) {
        content = c;
    }

    public void setImg(String image) { img = image;}

    public static class MessageBuilder{
        private int id;
        private String author;
        private String content;
        private String img;

        public MessageBuilder id(int id){
            this.id = id;
            return this;
        }

        public MessageBuilder author(String author){
            this.author = author;
            return this;
        }

        public MessageBuilder content(String content){
            this.content = content;
            return this;
        }

        public MessageBuilder img(String img){
            this.img = img;
            return this;
        }

        public Message build(){
            return new Message(author,content,img);
        }

    }

}

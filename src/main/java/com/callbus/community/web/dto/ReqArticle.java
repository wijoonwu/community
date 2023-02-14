package com.callbus.community.web.dto;


import com.callbus.community.biz.domain.Article;
import com.callbus.community.biz.domain.Member;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ReqArticle {

    private long id;
//    private Member member;

    private String writer;
    private String title;
    private String content;

//    public Article toEntity() {
//        return new Article(writer, member, title, content);
//    }

    public Article toEntity() {
        return new Article(writer, title, content);
    }
//
//    public void setMember(Member member){
//        this.member = member;
//    }

    public void setWriter(String writer){
        this.writer = writer;
    }
}

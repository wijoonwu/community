package com.callbus.community.web.dto;

import com.callbus.community.biz.domain.Article;
import com.callbus.community.biz.domain.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ArticleForm {
    private long id;
    private Member member;
    private String title;
    private String content;

    public Article toEntity() {
        return new Article(member, title, content);
    }

}

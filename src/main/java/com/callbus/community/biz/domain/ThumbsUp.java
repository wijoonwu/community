package com.callbus.community.biz.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class ThumbsUp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Article article;

    public ThumbsUp(Member member, Article article){
        this.member = member;
        this.article = article;
        article.setThumbsUps(article.getThumbsUps() + 1);
    }

    public void ThumbsDown(){
        article.setThumbsUps(article.getThumbsUps() - 1);
    }

}

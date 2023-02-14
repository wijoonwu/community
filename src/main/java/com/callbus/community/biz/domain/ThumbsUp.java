package com.callbus.community.biz.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ThumbsUp {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Article article;

    @Column(nullable = false)
    private boolean status;

    public ThumbsUp(Member member, Article article){
        this.member = member;
        this.article = article;
        this.status = true;
        article.setThumbsUps(article.getThumbsUps() + 1);
    }

    public void ThumbsDown(){
        this.status = false;
        article.setThumbsUps(article.getThumbsUps() - 1);
    }

}

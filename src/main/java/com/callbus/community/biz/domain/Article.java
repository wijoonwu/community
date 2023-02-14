package com.callbus.community.biz.domain;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Article extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Member member;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @OneToMany
    @JoinColumn(name = "THUMBS_UP_ID")
    private List<ThumbsUp> thumbsUp;

    private int thumbsUps;

    public Article(Member member, String title, String content) {
        this.member = member;
        this.title = title;
        this.content = content;
    }

    public void setThumbsUps(int i){
        this.thumbsUps = i;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setThumbsUp(ThumbsUp thumbsUp) {
        this.thumbsUp.add(thumbsUp);
    }
}

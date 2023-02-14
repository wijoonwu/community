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
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @ManyToOne
//    @JoinColumn(nullable = false)
//    private Member member;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @OneToMany
    @JoinColumn(name = "REPLY_ID")
    private List<Reply> reply;

    @OneToMany
    @JoinColumn(name = "THUMBS_UP_ID")
    private List<ThumbsUp> thumbsUp;

    public Article(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }
//    public Article(String writer, Member member, String title, String content) {
//        this.writer = writer;
//        this.member = member;
//        this.title = title;
//        this.content = content;
//    }
}

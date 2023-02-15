package com.callbus.community.web.dto;

import com.callbus.community.biz.domain.Article;
import com.callbus.community.biz.domain.Member;
import com.callbus.community.biz.domain.ThumbsUp;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ArticleDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResArticle{
        private long id;
        private String writer;
        private String title;
        private String content;
        private int thumbsUpCount;
        private boolean thumbsUpStatus;
        private String createdDate;
        private String modifiedDate;


        public ResArticle(Article article, String accountId) {
            this.createdDate = article.getCreatedDate();
            this.modifiedDate = article.getModifiedDate();
            this.id = article.getId();
            this.writer = article.getMember().getNickname() + "(" + article.getMember().getAccountType().getDescription() + ")";
            this.title = article.getTitle();
            this.content = article.getContent();
            this.thumbsUpCount = article.getThumbsUps();
            this.thumbsUpStatus = setThumbsUpStatus(article,accountId);
        }

        public boolean setThumbsUpStatus(Article article, String accountId) {
            if(article.getThumbsUp() != null){
                for(ThumbsUp thumbsUp : article.getThumbsUp()){
                    if (Objects.equals(thumbsUp.getMember().getAccountId(), accountId)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            return false;
        }

        public boolean getThumbsUpStatus() {
            return thumbsUpStatus;
        }
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ArticleForm{
        private String title;
        private String content;
        private Member member;

        public Article toEntity() {
            return new Article(member, title, content);
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteArticle{
        private long id;
        private String message;
        private String deletedDate;

        public DeleteArticle(Article article){
            this.id = article.getId();
            this.message = "삭제된 게시글입니다.";
            this.deletedDate = article.getDeletedDate();
        }
    }


}

package com.callbus.community.web.dto;

import com.callbus.community.biz.domain.Article;
import com.callbus.community.biz.domain.Member;
import com.callbus.community.biz.domain.ThumbsUp;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class ArticleDto {

    @Getter
    @ToString
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
            this.thumbsUpStatus = checkThumbsUpStatus(article,accountId);
        }

         boolean checkThumbsUpStatus(Article article, String accountId) {
            if(article.getThumbsUp() != null && article.getThumbsUps() > 0){
                for(ThumbsUp thumbsUp : article.getThumbsUp()){
                    if (Objects.equals(thumbsUp.getMember().getAccountId(), accountId)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ArticleForm{
        @NotBlank
        private String title;
        @NotBlank
        private String content;
        private Member member;

        public ArticleForm(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public void setMember(Member member) {
            this.member = member;
        }

        public Article toEntity() {
            return new Article(member, title, content);
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteArticle{
        private String message;
        private String deletedDate;

        public DeleteArticle(Article article){
            this.message = article.getId() + "번 게시글은 삭제되었습니다.";
            this.deletedDate = article.getDeletedDate();
        }
    }


}

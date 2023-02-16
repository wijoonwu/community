package com.callbus.community.biz.service;

import com.callbus.community.biz.domain.Article;
import com.callbus.community.biz.exception.CustomException;
import com.callbus.community.biz.repository.ArticleRepository;
import com.callbus.community.biz.repository.MemberRepository;
import com.callbus.community.web.dto.ArticleDto.ArticleForm;
import com.callbus.community.web.dto.ArticleDto.ResArticle;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Autowired
    ArticleRepository articleRepository;

    @Test
    @DisplayName("게시글 작성")
     void createArticle() {
        //given
        String title = "안녕하세요.";
        String content = "반갑습니다";
        String accountId = "Realtor 1";

        ArticleForm articleForm = new ArticleForm(title, content);

        //when
        ResArticle articleDto = articleService.createArticle(articleForm, accountId);
        Optional<Article> article = articleRepository.findById(articleDto.getId());

        //then
        Assert.assertNotNull(article);
        Assert.assertEquals(title , article.get().getTitle());
        Assert.assertEquals(content, article.get().getContent());
    }

    @Test
    @DisplayName("외부인 게시글 작성 불가")
    void unableToPost(){
        //given
        String title = "안녕하세요.";
        String content = "반갑습니다";

        ArticleForm articleForm = new ArticleForm(title, content);

        //then
        Assert.assertThrows(CustomException.class,
            () -> {
                //when
                articleService.createArticle(articleForm, null);
            });
    }

    @Test
    @DisplayName("게시글 단건 조회")
    void readArticle() {
        //given
        long articleId = 1L;
        String accountId = "Realtor 1";

        //when
        ResArticle articleDto = (ResArticle) articleService.readArticle(articleId, accountId);

        //then
        Assert.assertEquals(articleRepository.findById(articleId).get().getTitle(), articleDto.getTitle());
        Assert.assertEquals(articleRepository.findById(articleId).get().getContent(), articleDto.getContent());
        Assert.assertEquals(articleRepository.findById(articleId).get().getCreatedDate(), articleDto.getCreatedDate());
    }

    @Test
    @DisplayName("게시글 전체 조회")
    void readArticles() {
        //when
        List<ResArticle> articleDtoList = articleService.readArticles(null);

        //then
        Assert.assertEquals(articleRepository.findAll().size(), articleDtoList.size());
    }

    @Test
    @DisplayName("게시글 수정")
    void updateArticle() {
        //given
        long articleId = 1L;
        String title = "첫번째로 글 씁니다!";
        String content = "하하하하하하";
        String accountId = "Realtor 1";

        ArticleForm articleForm = new ArticleForm(title, content);

        //when
        articleService.updateArticle(articleId, articleForm, accountId);
        Optional<Article> article = articleRepository.findById(articleId);

        //then
        Assert.assertEquals(title, article.get().getTitle());
    }

    @Test
    @DisplayName("게시글 삭제")
    void deleteArticle() {
        //given
        long articleId = 1L;
        String accountId = "Realtor 1";
        int beforeDelete = articleRepository.findAll().size();

        //when
        articleService.deleteArticle(articleId, accountId);

        //then
        Optional<Article> article = articleRepository.findById(articleId);
        article.ifPresent(value -> Assert.assertNotNull(value.getDeletedDate()));
        Assert.assertNotEquals(beforeDelete, articleRepository.findAll().size());
    }

    @Test
    @DisplayName("작성자 외 삭제 불가")
    void unableToDelete(){
        //given
        long articleId = 1L;
        String accountId = "Realtor 2";

        //then
        Assert.assertThrows(CustomException.class,
        () ->
            //when
            articleService.deleteArticle(articleId, accountId));
    }

    @Test
    @DisplayName("작성자 외 수정 불가")
    void unableToUpdate(){
        //given
        long articleId = 1L;
        String title = "첫번째로 글 씁니다!";
        String content = "작성자는 제가 아니지만... 바꿔볼게요";
        ArticleForm articleForm = new ArticleForm(title, content);
        String accountId = "Realtor 2";

        //then
        Assert.assertNotEquals(accountId, articleRepository.findById(articleId).get().getMember().getAccountId());
        Assert.assertThrows(CustomException.class,
            () ->
                //when
                articleService.updateArticle(articleId, articleForm, accountId));
    }

}
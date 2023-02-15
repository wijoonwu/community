package com.callbus.community.biz.service;

import com.callbus.community.biz.domain.Article;
import com.callbus.community.biz.exception.CustomException;
import com.callbus.community.biz.repository.ArticleRepository;
import com.callbus.community.biz.repository.MemberRepository;
import com.callbus.community.web.dto.ArticleDto;
import com.callbus.community.web.dto.ArticleDto.ArticleForm;
import com.callbus.community.web.dto.ArticleDto.ResArticle;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
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
    MemberRepository memberRepository;

    @Autowired
    ArticleService articleService;

    @Autowired
    ArticleRepository articleRepository;

    @Test
    public void 게시글_작성() {
        //given
        ArticleForm articleForm = new ArticleForm();
        articleForm.setTitle("안녕하세요.");
        articleForm.setContent("반갑습니다");

        String accountId = "Realtor 1";

        //when
        ResArticle articleDto = articleService.createArticle(articleForm, accountId);

        //then
        Optional<Article> article = articleRepository.findById(articleDto.getId());
        Assert.assertNotNull(article);
    }

    @Test
    public void 외부인_게시글_작성_불가(){
        //given
        ArticleForm articleForm = new ArticleForm();
        articleForm.setTitle("안녕하세요.");
        articleForm.setContent("반갑습니다");

        //then
        Assert.assertThrows(CustomException.class,
            () -> {
                //when
                articleService.createArticle(articleForm, null);
            });

    }

    @Test
    public void 게시글_단건_조회() {
        //given
        long id = 1;
        String accountId = "Realtor 1";
        Optional<Article> article = articleRepository.findById(id);
        String title = article.get().getTitle();

        //when
        articleService.readArticle(1, accountId);

        //then
        Assert.assertEquals("첫 게시글 입니다!", title);
    }

    @Test
    public void 게시글_전체_조회() {
        //when
        List<ResArticle> articleDtoList = articleService.readArticles(null);

        //then
        Assert.assertEquals(2, articleDtoList.size());
    }

    @Test
    public void 게시글_수정() {
        //given
        long articleId = 1l;
        ArticleForm articleForm = new ArticleForm();
        articleForm.setTitle("첫번째로 글 씁니다!");
        articleForm.setContent("하하하하하하");
        String accountId = "Realtor 1";

        //when
        articleService.updateArticle(articleId, articleForm, accountId);
        Article article = articleRepository.findById(articleId).get();
        String title = article.getTitle();

        //then
        Assert.assertEquals("첫번째로 글 씁니다!", title);
    }

    @Test
    public void 게시글_삭제() {
        //given
        long articleId = 1l;
        String accountId = "Realtor 1";

        //when
        articleService.deleteArticle(articleId, accountId);

        //then
        Optional<Article> article = articleRepository.findById(articleId);
        Assert.assertEquals(Optional.empty(), article);
    }

    @Test
    public void 작성자_외_삭제불가(){
        //given
        long articleId = 1l;
        String accountId = "Realtor 2";

        //then
        Assert.assertThrows(CustomException.class,
        () ->
            //when
            articleService.deleteArticle(articleId, accountId));
    }

    @Test
    public void 작성자_외_수정불가(){
        //given
        long articleId = 1l;
        ArticleForm articleForm = new ArticleForm();
        articleForm.setTitle("첫번째로 글 씁니다!");
        articleForm.setContent("작성자는 제가 아니지만... 바꿔볼게요");
        String accountId = "Realtor 2";

        //then
        Assert.assertThrows(CustomException.class,
            () ->
                //when
                articleService.updateArticle(articleId, articleForm, accountId));
    }

}
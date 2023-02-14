package com.callbus.community.biz.service;

import static org.junit.jupiter.api.Assertions.*;

import com.callbus.community.biz.repository.ArticleRepository;
import com.callbus.community.biz.repository.MemberRepository;
import com.callbus.community.biz.repository.ThumbsUpRepository;
import com.callbus.community.web.dto.ArticleDto;
import java.util.List;
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
class ThumbsUpServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ArticleService articleService;

    @Autowired
    ThumbsUpService thumbsUpService;

    @Autowired
    ThumbsUpRepository thumbsUpRepository;


    @Test
    public void 좋아요_누르기() {
        //given
        long articleId = 1L;
        String accountId = "Realtor 1";
        thumbsUpService.createThumbsUp(articleId, accountId);

        //when
        ArticleDto articleDto = articleService.readArticle(articleId, accountId);
        boolean thumbsUpStatus = articleDto.getThumbsUpStatus();

        //then
        Assert.assertTrue(thumbsUpStatus);
    }

    @Test
    public void 좋아요_취소(){
        //given
        long articleId = 1L;
        String accountId = "Realtor 1";

        thumbsUpService.createThumbsUp(articleId, accountId);
        thumbsUpService.createThumbsUp(articleId, accountId);

        //when
        ArticleDto articleDto = articleService.readArticle(articleId, accountId);
        boolean thumbsUpStatus = articleDto.getThumbsUpStatus();

        //then
        Assert.assertFalse(thumbsUpStatus);

    }

    @Test
    public void 좋아요_게시글_보기() {
        //given
        long articleId = 1L;
        String accountId = "Realtor 1";
        thumbsUpService.createThumbsUp(articleId, accountId);

        //when
        List<ArticleDto> articleDtoList = thumbsUpService.readThumbsUpList(accountId);

        //then
        Assert.assertEquals(1, articleDtoList.size());
    }
}
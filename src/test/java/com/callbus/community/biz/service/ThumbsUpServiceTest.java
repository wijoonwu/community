package com.callbus.community.biz.service;

import static org.junit.jupiter.api.Assertions.*;

import com.callbus.community.biz.domain.Member;
import com.callbus.community.biz.repository.ArticleRepository;
import com.callbus.community.biz.repository.MemberRepository;
import com.callbus.community.biz.repository.ThumbsUpRepository;
import com.callbus.community.web.dto.ArticleDto.ResArticle;
import java.util.List;
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
    @DisplayName("좋아요 누르기")
    void createThumbsUp() {
        //given
        long articleId = 1L;
        String accountId = "Realtor 1";

        //when
        thumbsUpService.createThumbsUp(articleId, accountId);

        ResArticle articleDto = (ResArticle) articleService.readArticle(articleId, accountId);
        boolean thumbsUpStatus = articleDto.getThumbsUpStatus();

        //then
        Assert.assertTrue(thumbsUpStatus);
    }

    @Test
    @DisplayName("좋아요한 게시글 조회")
    void readThumbsUps() {
        //given
        long articleId = 1L;
        String accountId = "Realtor 1";
        Member member = memberRepository.findByAccountId(accountId);
        thumbsUpService.createThumbsUp(articleId, accountId);

        //when
        List<ResArticle> articleDtoList = thumbsUpService.readThumbsUpList(accountId);

        //then
        Assert.assertEquals(thumbsUpRepository.findAllByMember(member).size(), articleDtoList.size());
    }
}
package com.callbus.community.biz.service;

import static org.junit.jupiter.api.Assertions.*;

import com.callbus.community.biz.domain.Member;
import com.callbus.community.biz.repository.MemberRepository;
import com.callbus.community.biz.repository.ThumbsUpRepository;
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
class ThumbsUpServiceTest {

    @Autowired
    MemberRepository memberRepository;

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
        String thumbsUpAccountId = "Realtor 1";

        //when
        String message = thumbsUpService.createThumbsUp(articleId, thumbsUpAccountId);

        ResArticle thumbsUpArticleDto = (ResArticle) articleService.readArticle(articleId, thumbsUpAccountId);
        ResArticle defaultArticleDto = (ResArticle) articleService.readArticle(articleId, null);

        boolean thumbsUpStatus = thumbsUpArticleDto.getThumbsUpStatus();
        boolean defaultStatus = defaultArticleDto.getThumbsUpStatus();

        //then
        Assert.assertEquals("좋아요를 눌렀습니다.", message);
        Assert.assertTrue(thumbsUpStatus);
        Assert.assertFalse(defaultStatus);
    }

    @Test
    @DisplayName("좋아요 취소")
    void thumbsDown() {
        //given
        long articleId = 1L;
        String accountId = "Realtor 1";

        //when
        String message1 = thumbsUpService.createThumbsUp(articleId, accountId);
        String message2 = thumbsUpService.createThumbsUp(articleId, accountId);

        //then
        Assert.assertEquals("좋아요를 눌렀습니다.", message1);
        Assert.assertEquals("좋아요가 취소되었습니다.", message2);
    }

    @Test
    @DisplayName("좋아요한 게시글 조회")
    void readThumbsUps() {
        //given
        long articleId = 1L;
        String accountId = "Realtor 1";
        Optional<Member> member = memberRepository.findByAccountId(accountId);
        thumbsUpService.createThumbsUp(articleId, accountId);

        //when
        List<ResArticle> articleDtoList = thumbsUpService.readThumbsUpList(accountId);

        //then
        member.ifPresent(
            value -> Assert.assertEquals(thumbsUpRepository.findAllByMember(value).size(),
                articleDtoList.size()));
    }
}
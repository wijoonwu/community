package com.callbus.community.biz.service;

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
        String realtorAccountId = "Realtor 1";
        String lessorAccountId = "Lessor 1";

        //when
        String message = thumbsUpService.createThumbsUp(articleId, realtorAccountId);
        thumbsUpService.createThumbsUp(articleId, lessorAccountId);

        ResArticle thumbsUpArticleDto = (ResArticle) articleService.readArticle(articleId, realtorAccountId);
        ResArticle defaultArticleDto = (ResArticle) articleService.readArticle(articleId, null);

        boolean thumbsUpStatus = thumbsUpArticleDto.isThumbsUpStatus();
        boolean defaultStatus = defaultArticleDto.isThumbsUpStatus();

        //then
        Assert.assertEquals("좋아요를 눌렀습니다.", message);
        Assert.assertTrue(thumbsUpStatus);
        Assert.assertFalse(defaultStatus);
        Assert.assertEquals(2, defaultArticleDto.getThumbsUpCount());
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
        ResArticle article = (ResArticle) articleService.readArticle(articleId, accountId);

        //then
        Assert.assertEquals("좋아요를 눌렀습니다.", message1);
        Assert.assertEquals("좋아요가 취소되었습니다.", message2);
        Assert.assertEquals(0, article.getThumbsUpCount());
        Assert.assertEquals(false, article.isThumbsUpStatus());
    }

    @Test
    @DisplayName("좋아요한 게시글 조회")
    void readThumbsUps() {
        //given
        String accountId = "Realtor 1";
        Optional<Member> member = memberRepository.findByAccountId(accountId);
        thumbsUpService.createThumbsUp(1L, accountId);
        thumbsUpService.createThumbsUp(2L, accountId);

        //when
        List<ResArticle> articleDtoList = thumbsUpService.readThumbsUpList(accountId);

        //then
        Assert.assertEquals(articleDtoList.size(), thumbsUpRepository.findAllByMember(member.get()).size());
        for(ResArticle article : articleDtoList){
            Assert.assertTrue(article.isThumbsUpStatus());
        }
    }
}
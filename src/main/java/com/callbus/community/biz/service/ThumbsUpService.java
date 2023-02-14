package com.callbus.community.biz.service;

import com.callbus.community.biz.domain.Article;
import com.callbus.community.biz.domain.Member;
import com.callbus.community.biz.domain.ThumbsUp;
import com.callbus.community.biz.domain.properties.ErrorCode;
import com.callbus.community.biz.exception.CustomException;
import com.callbus.community.biz.repository.ArticleRepository;
import com.callbus.community.biz.repository.MemberRepository;
import com.callbus.community.biz.repository.ThumbsUpRepository;
import com.callbus.community.web.dto.ArticleDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class ThumbsUpService {

    private final ThumbsUpRepository thumbsUpRepository;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public String createThumbsUp(long id, String accountId) {
        Article article = articleRepository.findById(id).orElseThrow(
            () -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND)
        );
        Member member = memberRepository.findByAccountId(accountId);
        ThumbsUp target = thumbsUpRepository.findByArticleAndMember(article, member);

        if(ObjectUtils.isEmpty(target)){
            ThumbsUp thumbsUp = new ThumbsUp(member, article);
            thumbsUpRepository.save(thumbsUp);
            article.setThumbsUp(thumbsUp);
            return "좋아요를 눌렀습니다.";
        } else {
            target.ThumbsDown();
            thumbsUpRepository.delete(target);
            return "좋아요가 취소되었습니다.";
        }
    }

    @Transactional(readOnly = true)
    public List<ArticleDto> readThumbsUpList(String accountId){
        Member member = memberRepository.findByAccountId(accountId);
        List<ThumbsUp> thumbsUps = thumbsUpRepository.findAllByMember(member);
        List<ArticleDto> articleDtoList = new ArrayList<>();

        for (ThumbsUp thumbsUp : thumbsUps) {
            Article article = articleRepository.findByThumbsUp(thumbsUp);
            ArticleDto resArticle = new ArticleDto(article, accountId);
            articleDtoList.add(resArticle);
        }
        return articleDtoList;
    }
}

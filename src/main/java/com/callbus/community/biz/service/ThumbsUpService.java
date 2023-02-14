package com.callbus.community.biz.service;

import com.callbus.community.biz.domain.Article;
import com.callbus.community.biz.domain.Member;
import com.callbus.community.biz.domain.ThumbsUp;
import com.callbus.community.biz.domain.properties.ErrorCode;
import com.callbus.community.biz.exception.CustomException;
import com.callbus.community.biz.repository.ArticleRepository;
import com.callbus.community.biz.repository.MemberRepository;
import com.callbus.community.biz.repository.ThumbsUpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ThumbsUpService {

    private final ThumbsUpRepository thumbsUpRepository;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public ThumbsUp thumbsUp(long id, String accountId) {
        Article article = articleRepository.findById(id).orElseThrow(
            () -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND)
        );
        Member member = memberRepository.findByAccountId(accountId);
        ThumbsUp thumbsUp = new ThumbsUp();
        thumbsUp.setArticle(article);
        thumbsUp.setMember(member);
        thumbsUpRepository.save(thumbsUp);
        return thumbsUp;
    }
}

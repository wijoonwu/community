package com.callbus.community.biz.service;

import com.callbus.community.biz.domain.Article;
import com.callbus.community.biz.domain.Member;
import com.callbus.community.biz.domain.properties.ErrorCode;
import com.callbus.community.biz.exception.CustomException;
import com.callbus.community.biz.repository.ArticleRepository;
import com.callbus.community.biz.repository.MemberRepository;
import com.callbus.community.web.dto.ReqArticle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Article createArticle(ReqArticle articleForm, String id) {
        try {
            Member member = memberRepository.findByAccountId(id);
//            articleForm.setMember(member);
            String writer = member.getNickname() + "(" + member.getAccountType().getDescription() + ")";
            articleForm.setWriter(writer);
            Article article = articleForm.toEntity();
            articleRepository.save(article);
            return article;
        } catch (NullPointerException e) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
    }
    @Transactional(readOnly = true)
    public Article readArticle(long id) {
        Article article = articleRepository.findById(id).orElseThrow(
            () -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
        return article;
    }
}

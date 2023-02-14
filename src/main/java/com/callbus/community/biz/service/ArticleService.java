package com.callbus.community.biz.service;

import com.callbus.community.biz.domain.Article;
import com.callbus.community.biz.domain.Member;
import com.callbus.community.biz.domain.properties.ErrorCode;
import com.callbus.community.biz.exception.CustomException;
import com.callbus.community.biz.repository.ArticleRepository;
import com.callbus.community.biz.repository.MemberRepository;
import com.callbus.community.web.dto.ArticleDto;
import com.callbus.community.web.dto.ArticleForm;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ArticleDto createArticle(ArticleForm articleForm, String accountId) {
        try {
            Member member = memberRepository.findByAccountId(accountId);
            articleForm.setMember(member);
            Article article = articleForm.toEntity();
            articleRepository.save(article);
            return new ArticleDto(article, accountId);
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
    }

    @Transactional(readOnly = true)
    public ArticleDto readArticle(long id, String accountId) {
        Article article = articleRepository.findById(id).orElseThrow(
        () -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
        return new ArticleDto(article, accountId);
    }

    @Transactional(readOnly = true)
    public List<ArticleDto> readArticles(String accountId) {
        List<Article> articleList = articleRepository.findAll();
        List<ArticleDto> articleDtoList = new ArrayList<>();
        for(Article article : articleList){
            articleDtoList.add(new ArticleDto(article, accountId));
        }
        return articleDtoList;
    }

    @Transactional
    public ArticleDto updateArticle(long articleId, ArticleForm articleForm, String accountId) {
        Article article = articleRepository.findById(articleId).orElseThrow(
            () -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
        Member member = memberRepository.findByAccountId(accountId);

        if(article.getMember() != member){
           throw new CustomException(ErrorCode.THE_WRONG_APPROACH);
        }

        article.setTitle(articleForm.getTitle());
        article.setContent(articleForm.getContent());
        return new ArticleDto(article, accountId);
    }

    @Transactional
    public String deleteArticle(long articleId, String accountId) {
        Article article = articleRepository.findById(articleId).orElseThrow(
            () -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
        Member member = memberRepository.findByAccountId(accountId);

        if(article.getMember() != member){
            throw new CustomException(ErrorCode.THE_WRONG_APPROACH);
        }

        articleRepository.delete(article);
        return "게시글을 삭제 했습니다";
    }

}

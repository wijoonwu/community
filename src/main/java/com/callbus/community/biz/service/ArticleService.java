package com.callbus.community.biz.service;

import com.callbus.community.biz.domain.Article;
import com.callbus.community.biz.domain.Member;
import com.callbus.community.biz.domain.properties.ErrorCode;
import com.callbus.community.biz.exception.CustomException;
import com.callbus.community.biz.repository.ArticleRepository;
import com.callbus.community.biz.repository.MemberRepository;
import com.callbus.community.web.dto.ArticleDto.ArticleForm;
import com.callbus.community.web.dto.ArticleDto.DeleteArticle;
import com.callbus.community.web.dto.ArticleDto.ResArticle;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public ResArticle createArticle(ArticleForm articleForm, String accountId) {
        try {
            Optional<Member> member = memberRepository.findByAccountId(accountId);
            if(member.isPresent()){
                articleForm.setMember(member.get());
                Article article = articleForm.toEntity();
                articleRepository.save(article);
                return new ResArticle(article, accountId);
            }
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
        throw new CustomException(ErrorCode.THE_WRONG_APPROACH);
    }

    @Transactional(readOnly = true)
    public Object readArticle(long articleId, String accountId) {
        Optional<Article> article = articleRepository.findById(articleId);
        if(article.isPresent()){
            if(article.get().getDeletedDate() == null) {
                return new ResArticle(article.get(), accountId);
            } return new DeleteArticle(article.get());
        }
        throw new CustomException(ErrorCode.ARTICLE_NOT_FOUND);
    }

    @Transactional(readOnly = true)
    public List<ResArticle> readArticles(String accountId) {
        List<Article> articleList = articleRepository.findAll();
        List<ResArticle> articleDtoList = new ArrayList<>();
        for(Article article : articleList){
            articleDtoList.add(new ResArticle(article, accountId));
        }
        return articleDtoList;
    }

    public ResArticle updateArticle(long articleId, ArticleForm articleForm, String accountId) {
        Optional<Article> article = articleRepository.findById(articleId);
        if(article.isPresent()){
            checkWriter(accountId, article.get());
            article.get().setTitle(articleForm.getTitle());
            article.get().setContent(articleForm.getContent());
            return new ResArticle(article.get(), accountId);
        }
        throw new CustomException(ErrorCode.ARTICLE_NOT_FOUND);
    }

    public String deleteArticle(long articleId, String accountId) {
        Optional<Article> article = articleRepository.findById(articleId);
        if(article.isPresent()){
            checkWriter(accountId, article.get());
            articleRepository.delete(article.get());
            return "게시글을 삭제 했습니다";
        }
        throw new CustomException(ErrorCode.ARTICLE_NOT_FOUND);
    }

    private void checkWriter(String accountId, Article article) {
        Optional<Member> member = memberRepository.findByAccountId(accountId);
        if(member.isPresent()){
            if(article.getMember() != member.get()){
                throw new CustomException(ErrorCode.THE_WRONG_APPROACH);
            }
        } else throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
    }

}

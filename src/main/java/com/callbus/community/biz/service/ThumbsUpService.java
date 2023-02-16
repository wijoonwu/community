package com.callbus.community.biz.service;

import com.callbus.community.biz.domain.Article;
import com.callbus.community.biz.domain.Member;
import com.callbus.community.biz.domain.ThumbsUp;
import com.callbus.community.biz.domain.properties.ErrorCode;
import com.callbus.community.biz.exception.CustomException;
import com.callbus.community.biz.repository.ArticleRepository;
import com.callbus.community.biz.repository.MemberRepository;
import com.callbus.community.biz.repository.ThumbsUpRepository;
import com.callbus.community.web.dto.ArticleDto.ResArticle;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class ThumbsUpService {

    private final ThumbsUpRepository thumbsUpRepository;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    public String createThumbsUp(long id, String accountId) {
        Article article = articleRepository.findById(id).orElseThrow(
            () -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
        Optional<Member> member = memberRepository.findByAccountId(accountId);
        if (ObjectUtils.isEmpty(member)) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
        if(member.isPresent()){
            Optional<ThumbsUp> target = thumbsUpRepository.findByArticleAndMember(article, member.get());
            if(target.isPresent()){
                target.get().ThumbsDown();
                thumbsUpRepository.delete(target.get());
                return "좋아요가 취소되었습니다.";
            }
            ThumbsUp thumbsUp = new ThumbsUp(member.get(), article);
            thumbsUpRepository.save(thumbsUp);
            article.setThumbsUp(thumbsUp);
        }
        return "좋아요를 눌렀습니다.";
    }

    @Transactional(readOnly = true)
    public List<ResArticle> readThumbsUpList(String accountId) {
        Optional<Member> member = memberRepository.findByAccountId(accountId);
        List<ResArticle> articleDtoList = new ArrayList<>();
        if(member.isPresent()) {
            List<ThumbsUp> thumbsUps = thumbsUpRepository.findAllByMember(member.get());
            try {
                for (ThumbsUp thumbsUp : thumbsUps) {
                    Optional<Article> article = articleRepository.findByThumbsUp(thumbsUp);
                    if (article.isPresent()) {
                        if (article.get().getDeletedDate() == null) {
                            ResArticle resArticle = new ResArticle(article.get(), accountId);
                            articleDtoList.add(resArticle);
                        }
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return articleDtoList;
    }
}

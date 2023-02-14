package com.callbus.community.biz.repository;

import com.callbus.community.biz.domain.Article;
import com.callbus.community.biz.domain.Member;
import com.callbus.community.biz.domain.ThumbsUp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThumbsUpRepository extends JpaRepository<ThumbsUp, Long> {

    ThumbsUp findByArticleAndMember(Article article, Member member);
}

package com.callbus.community.biz.repository;

import com.callbus.community.biz.domain.Article;
import com.callbus.community.biz.domain.ThumbsUp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long> {

    Article findByThumbsUp(ThumbsUp thumbsUp);
}

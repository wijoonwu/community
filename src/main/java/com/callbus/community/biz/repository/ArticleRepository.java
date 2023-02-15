package com.callbus.community.biz.repository;

import com.callbus.community.biz.domain.Article;
import com.callbus.community.biz.domain.ThumbsUp;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article,Long> {

    Article findByThumbsUp(ThumbsUp thumbsUp);

    Optional<Article> findById(long id);

    @Query("select a from Article a where a.deletedDate is null")
    List<Article> findAll();
}
